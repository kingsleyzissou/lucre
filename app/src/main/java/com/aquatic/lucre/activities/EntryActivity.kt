package com.aquatic.lucre.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.EntryType
import com.aquatic.lucre.models.Location
import com.aquatic.lucre.utilities.readImage
import com.aquatic.lucre.utilities.showImagePicker
import com.aquatic.lucre.viewmodels.CategoryViewModel
import com.aquatic.lucre.viewmodels.EntryViewModel
import kotlinx.android.synthetic.main.activity_entry.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2
var location = Location(52.245696, -7.139102, 15f)

class EntryActivity : AppCompatActivity(), AnkoLogger {

    var entry = Entry()
    var vault: String? = null

    // spinners
    var types = mutableListOf("Income", "Expense")
    lateinit var category: SpinnerActivity<Category>
    lateinit var type: SpinnerActivity<String>

    lateinit var app: App

    val categoryModel: CategoryViewModel by viewModels()
    val model: EntryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        app = application as App

        type = SpinnerActivity(this, entryType, types)

        entryImageButton.setOnClickListener { selectImage() }
        entryLocation.setOnClickListener { setLocation() }
        entrySubmit.setOnClickListener { submit() }

        handleIntent()
        getCategories()

    }

    private fun getCategories() {
        categoryModel.list.observe(this, Observer { categories ->
            category = SpinnerActivity(this, entryCategory, categories)
            if (intent.hasExtra("entry_edit")) {
                category.setSelectedItem(categories.find{ it.id.equals(entry.category) }!!)
            }
        })
    }

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
            }
            entrySubmit.setText(R.string.item_edit)
        }
    }

    private fun setLocation() {
        val location = Location(52.245696, -7.139102, 15f, entry.vendor!!)
        startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    private fun selectImage() {
        showImagePicker(this, IMAGE_REQUEST)
    }

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

    private fun validate(): Boolean {
        info("Validating")
        val message = getResources().getString(R.string.required)
        return entryAmount.validate(message) { it.isNotEmpty() } &&
            entryVendor.validate(message) { it.isNotEmpty() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    // permissions issues
                    // https://developer.android.com/training/data-storage/shared/documents-files#persist-permissions
                    val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    // Check for the freshest data.
                    contentResolver.takePersistableUriPermission(data.getData()!!, takeFlags)
                    entry.image = data.getData().toString()
                    entryImage.setImageURI(data.getData())
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable<Location>("location")!!
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_entry_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.entry_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
