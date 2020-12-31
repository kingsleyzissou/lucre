package com.aquatic.lucre.activities.entry

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.MapsActivity
import com.aquatic.lucre.activities.SpinnerActivity
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.EntryType
import com.aquatic.lucre.models.Location
import com.aquatic.lucre.utilities.readImage
import com.aquatic.lucre.utilities.showImagePicker
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
    var types = listOf("Income", "Expense")
    lateinit var categories: List<Category>
    lateinit var category: SpinnerActivity<Category>
    lateinit var type: SpinnerActivity<String>

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        app = application as App

        categories = app.categoryStore.all().toList()
        category = SpinnerActivity(this, entryCategory, categories)
        type = SpinnerActivity(this, entryType, types)

        if (intent.hasExtra("entry_edit")) {
            entry = intent.extras?.getParcelable<Entry>("entry_edit")!!
            entryAmount.setText(entry.amount.toString())
            entryVendor.setText(entry.vendor)
            type.setSelectedItem(entry.type.toString().toLowerCase())
            entryDescription.setText(entry.description)
            category.setSelectedItem(entry.category!!)
            if (entry.image.isNotEmpty()) {
                val bitmap = readImage(this, entry.image)
                info("$bitmap")
                entryImage.setImageBitmap(bitmap)
                entryImageButton.setText(R.string.entry_edit_image)
            }
            entrySubmit.setText(R.string.item_edit)
        }

        entryImageButton.setOnClickListener { selectImage() }
        entryLocation.setOnClickListener { setLocation() }
        entrySubmit.setOnClickListener { submit() }
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
            entry.type = EntryType.valueOf(type.selection!!.toUpperCase())
            entry.vendor = entryVendor.text.toString()
            entry.description = entryDescription.text.toString()
            entry.category = category.selection!!
            entry.location = location
            app.entryStore.create(entry.copy())
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }
    }

    private fun validate(): Boolean {
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
