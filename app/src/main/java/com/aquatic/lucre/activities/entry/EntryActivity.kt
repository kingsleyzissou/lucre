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
    lateinit var category: SpinnerActivity<String>
    lateinit var type: SpinnerActivity<String>

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        app = application as App
        category = SpinnerActivity(this, entryCategory, listOf("Bills", "Rent", "Salary"))
        type = SpinnerActivity(this, entryType, listOf("Income", "Expense"))

        entryAddToolbar.title = title
        setSupportActionBar(entryAddToolbar)

        entryAdd.setOnClickListener { submit() }
    }

    private fun submit() {
        if (validate()) {
            entry.amount = entryAmount.text.toString().toFloat()
            entry.type = EntryType.valueOf(type.selection!!.toUpperCase())
            entry.vendor = entryVendor.text.toString()
            entry.description = entryDescription.text.toString()
            entry.category = Category(category.selection)
        }
    }

    private fun validate(): Boolean {
        return entryVendor.validate("This field is required") { it.isNotEmpty() }
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
