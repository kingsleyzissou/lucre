package com.aquatic.lucre.activities.entry

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.SpinnerActivity
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.EntryType
import kotlinx.android.synthetic.main.activity_entry.*

class EntryActivity : AppCompatActivity() {

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
            entrySubmit.setText(R.string.item_edit)
        }

        entryAddToolbar.title = title
        setSupportActionBar(entryAddToolbar)

        entrySubmit.setOnClickListener { submit() }
    }

    private fun submit() {
        if (validate()) {
            entry.amount = entryAmount.text.toString().toFloat()
            entry.type = EntryType.valueOf(type.selection!!.toUpperCase())
            entry.vendor = entryVendor.text.toString()
            entry.description = entryDescription.text.toString()
            entry.category = category.selection
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
