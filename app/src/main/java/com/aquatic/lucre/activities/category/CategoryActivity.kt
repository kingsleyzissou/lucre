package com.aquatic.lucre.activities.category

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.viewmodels.CategoryViewModel
import com.skydoves.colorpickerview.listeners.ColorListener
import kotlinx.android.synthetic.main.activity_category.*
import org.jetbrains.anko.AnkoLogger

class CategoryActivity : AppCompatActivity(), AnkoLogger {

    /* Create an empty category */
    var category = Category()

    /* Android application object */
    lateinit var app: App

    /* Place holder for color hex string */
    var color: String? = null

    /* Category View Model by injection */
    val model: CategoryViewModel by viewModels()

    /**
     * Setup the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        app = application as App
        categorySubmit.setOnClickListener { submit() }
        categoryDelete.setOnClickListener { openDeleteDialog() }
        colorPickerView.setColorListener(colorViewListener())
        handleIntent()
    }

    /**
     * Update the color text placeholder
     * with the hexstring
     */
    private fun colorViewListener(): ColorListener {
        return ColorListener() { i: Int, _ ->
            // convert the color in to a hex string
            // https://stackoverflow.com/a/6540378
            color = String.format("#%06X", 0xFFFFFF and i)
            categoryColor.text = color
        }
    }

    /**
     * If there is an intent, we need to handle this
     * and set the associated data
     */
    private fun handleIntent() {
        if (intent.hasExtra("category_edit")) {
            category = intent.extras?.getParcelable<Category>("category_edit")!!
            categoryName.setText(category.name)
            categoryDescription.setText(category.description)
            color = category.color
            val intColor = Color.parseColor(category.color)
            colorPickerView.setInitialColor(intColor)
            categorySubmit.setText(R.string.item_edit)
        }
    }

    /**
     * Submit the add/edit request
     * to the firestore
     */
    private fun submit() {
        if (validate()) {
            category.name = categoryName.text.toString()
            category.description = categoryDescription.text.toString()
            category.color = color
            category.userId = app.user?.id
            model.saveCategory(category.copy())
            setResult(RESULT_OK)
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
        return categoryName.validate(message) { it.isNotEmpty() }
    }

    /**
     * Programmatically open the delete category
     * dialog
     */
    private fun openDeleteDialog() {
        AlertDialog.Builder(this)
            .setTitle("Delete category")
            .setMessage("Are you sure you want to delete this category?")
            .setCancelable(true)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                model.deleteCategory(category)
                finish()
            }
            .show()
    }

    /**
     * Add the cancel options menu for the activity
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_category_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Return to previous activity if activity is
     * cancelled
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category_cancel -> { finish() }
        }
        return super.onOptionsItemSelected(item)
    }
}
