package com.aquatic.lucre.activities.category

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import com.skydoves.colorpickerview.listeners.ColorListener
import kotlinx.android.synthetic.main.activity_category.*
import org.jetbrains.anko.AnkoLogger

class CategoryActivity : AppCompatActivity(), AnkoLogger {

    var category = Category()
    lateinit var app: App
    var color: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        app = application as App

        categoryAddToolbar.title = title
        setSupportActionBar(categoryAddToolbar)

        colorPickerView.setColorListener(
            ColorListener() { i: Int, b: Boolean ->
                // https://stackoverflow.com/a/6540378
                color = String.format("#%06X", 0xFFFFFF and i)
                categoryColor.text = color
            }
        )

        if (intent.hasExtra("category_edit")) {
            category = intent.extras?.getParcelable<Category>("category_edit")!!
            categoryName.setText(category.name)
            categoryDescription.setText(category.description)
            color = category.color
            var intColor = Color.parseColor(category.color)
            colorPickerView.setInitialColor(intColor)
            categorySubmit.setText(R.string.item_edit)
        }

        categorySubmit.setOnClickListener { submit() }
    }

    private fun submit() {
        if (validate()) {
            category.name = categoryName.text.toString()
            category.description = categoryDescription.text.toString()
            category.color = color
            app.categoryStore.create(category.copy())
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun validate(): Boolean {
        val message = getResources().getString(R.string.required)
        return categoryName.validate(message) { it.isNotEmpty() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_category_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
