package com.aquatic.lucre.activities.entry

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Predicate
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.ui.MapsActivity
import com.aquatic.lucre.activities.ui.SpinnerActivity
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.EntryType
import com.aquatic.lucre.models.Location
import com.aquatic.lucre.utilities.IMAGE_REQUEST
import com.aquatic.lucre.utilities.LOCATION_REQUEST
import com.aquatic.lucre.utilities.readImage
import com.aquatic.lucre.utilities.showImagePicker
import com.aquatic.lucre.viewmodels.CategoryViewModel
import com.aquatic.lucre.viewmodels.EntryViewModel
import kotlinx.android.synthetic.main.activity_entry.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor

class EntryActivity : AppCompatActivity(), AnkoLogger {

    /* Empty entry object */
    var entry = Entry()

    /* Vault id */
    var vault: String? = null

    /* Default location */
    var location = Location(52.245696, -7.139102, 15f)

    /* Entry expenses in list format */
    var types = mutableListOf("Income", "Expense")

    /* Spinner for category type */
    lateinit var category: SpinnerActivity<Category>

    /* Spinner for entry type */
    lateinit var type: SpinnerActivity<String>

    /* Android application object */
    lateinit var app: App

    /* Category ViewModel by injection */
    val categoryModel: CategoryViewModel by viewModels()

    /* Entry ViewModel by injection */
    val model: EntryViewModel by viewModels()

    /**
     * Setup the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        // instantiate application object
        app = application as App

        // set the options for the type spinner
        type = SpinnerActivity(this, entryType, types)

        entryImageButton.setOnClickListener { selectImage() }
        entryLocation.setOnClickListener { setLocation() }
        entrySubmit.setOnClickListener { submit() }
        entryDelete.setOnClickListener { openDeleteDialog() }

        handleIntent()
        getCategories()
    }

    /**
     * Get a list of categories for the categories
     * dropdown spinner
     */
    private fun getCategories() {
        categoryModel.list.observe(
            this,
            Observer { categories ->
                // once we have the categories, we can populate the spinner
                category = SpinnerActivity(this, entryCategory, categories)
                // if there is an intent, we need to set the category
                if (intent.hasExtra("entry_edit")) {
                    var predicate = Predicate<Category> { it.id === entry.category }
                    val needle = categoryModel.find(predicate, categories)
                    category.setSelectedItem(needle)
                }
            }
        )
    }
    /**
     * If there is an intent, we need to handle this
     * and set the associated data
     *
     * TODO pass in vault id
     */
    private fun handleIntent() {
        if (intent.hasExtra("entry_edit")) {
            entry = intent.extras?.getParcelable<Entry>("entry_edit")!!
            entryAmount.setText(entry.amount.toString())
            entryVendor.setText(entry.vendor)
            type.setSelectedItem(entry.type.toString().toLowerCase())
            entryDescription.setText(entry.description)
            if (entry.image.isNotEmpty()) {
                val bitmap = readImage(this, entry.image)
                entryImage.setImageBitmap(bitmap)
                entryImageButton.setText(R.string.entry_edit_image)
                entryImage.visibility = View.VISIBLE
            }
            entrySubmit.setText(R.string.item_edit)
        }
    }

    /**
     * Update the entry location
     */
    private fun setLocation() {
        location = Location(52.245696, -7.139102, 15f, entry.vendor!!)
        startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    /**
     * Launch the image picker
     */
    private fun selectImage() {
        showImagePicker(this, IMAGE_REQUEST)
    }

    /**
     * Submit the entry create/update request
     */
    private fun submit() {
        if (validate()) {
            entry.amount = entryAmount.text.toString().toFloat()
            entry.type = EntryType.valueOf(type.selection!!.toUpperCase()).toString()
            entry.vendor = entryVendor.text.toString()
            entry.description = entryDescription.text.toString()
            entry.category = category.selection?.id!!
            entry.location = location
            entry.userId = app.user?.id
            model.saveEntry(entry.copy())
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
    }

    /**
     * Validate the EditText fields
     * to make sure all inputs are
     * valid
     */
    private fun validate(): Boolean {
        val message = getResources().getString(R.string.required)
        return entryAmount.validate(message) { it.isNotEmpty() } &&
            entryVendor.validate(message) { it.isNotEmpty() }
    }

    /**
     * Programmatically open the delete category
     * dialog
     */
    private fun openDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?")
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                model.deleteEntry(entry)
                finish()
            }
            .show()
    }

    /**
     * Check if there have been any changes for image or location requests
     * and, if so, update the entry accordingly
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    // TODO Use firebase file upload
                    // permissions issues
                    // https://developer.android.com/training/data-storage/shared/documents-files#persist-permissions
                    val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    // Check for the freshest data.
                    contentResolver.takePersistableUriPermission(data.getData()!!, takeFlags)
                    entry.image = data.getData().toString()
                    entryImage.setImageURI(data.getData())
                    entryImage.visibility = View.VISIBLE
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable<Location>("location")!!
                }
            }
        }
    }

    /**
     * Add the cancel options menu for the activity
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_entry_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Return to previous activity if activity is
     * cancelled
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.entry_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}
