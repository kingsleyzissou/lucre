package com.aquatic.lucre.activities.entry

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.models.EntryType
import kotlinx.android.synthetic.main.activity_entry.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class CategorySpinner : AnkoLogger, AdapterView.OnItemSelectedListener {

    var selection: String? = null
    var options = listOf("Bills", "Rent", "Salary")

    constructor(parent: AppCompatActivity, spinner: Spinner) {
        spinner.setOnItemSelectedListener(this)
        val array_adapter = ArrayAdapter(parent, android.R.layout.simple_spinner_item, options)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(array_adapter)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selection = options[position]
        info("(Entry) We selected: $selection")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

class TypeSpinner : AnkoLogger, AdapterView.OnItemSelectedListener {

    var selection: String? = null
    var options = listOf("Income", "Expense")

    constructor(parent: AppCompatActivity, spinner: Spinner) {
        spinner.setOnItemSelectedListener(this)
        val array_adapter = ArrayAdapter(parent, android.R.layout.simple_spinner_item, options)
        array_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.setAdapter(array_adapter)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selection = options[position]
        info("(Type) We selected: $selection")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}

class EntryActivity : AppCompatActivity() {

    var entry = Entry()
    var vault: String? = null

    // spinners
    lateinit var category: CategorySpinner
    lateinit var type: TypeSpinner

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        app = application as App
        category = CategorySpinner(this, entryCategory)
        type = TypeSpinner(this, entryType)

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
