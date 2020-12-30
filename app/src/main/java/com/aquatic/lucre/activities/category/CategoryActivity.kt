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
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

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
                color = String.format("#%06X", 0xFFFFFF and i)
                categoryColor.text = color
                info("New color: $color")
            }
        )

        if (intent.hasExtra("category_edit")) {
            category = intent.extras?.getParcelable<Category>("category_edit")!!
            categoryName.setText(category.name)
            categoryDescription.setText(category.description)
            color = category.color
            var intColor = Color.parseColor(category.color)
            info("$intColor")
            colorPickerView.setInitialColor(intColor)
        }

        categoryAdd.setOnClickListener { submit() }
    }

    private fun submit() {
        if (validate()) {
            category.name = categoryName.text.toString()
            category.description = categoryDescription.text.toString()
            category.color = color
            app.categoryStore.create(category.copy())
            toast("Category created: $category")
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun validate(): Boolean {
        return categoryName.validate("Field is required") { it.isNotEmpty() }
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
