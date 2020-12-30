package com.aquatic.lucre.activities.category

import android.os.Bundle
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

    val category = Category()
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
}
