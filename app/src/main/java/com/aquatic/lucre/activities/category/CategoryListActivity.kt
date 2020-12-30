package com.aquatic.lucre.activities.category

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.CategoryAdapter
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import kotlinx.android.synthetic.main.activity_category_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult

class CategoryListActivity : AppCompatActivity(), AdapterListener<Category> {

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)

        app = application as App

        categoryListToolbar.title = title
        setSupportActionBar(categoryListToolbar)

        val layoutManager = LinearLayoutManager(this)
        categoryRecyclerView.layoutManager = layoutManager
        categoryRecyclerView.adapter = CategoryAdapter(app.categoryStore.all(), this)
    }

    override fun onResume() {
        super.onResume()
        categoryRecyclerView.adapter = CategoryAdapter(app.categoryStore.all(), this)
    }

    override fun onCardClick(category: Category) {
        startActivityForResult(
            intentFor<CategoryActivity>().putExtra("category_edit", category),
            0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_category_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category_add -> startActivityForResult<CategoryActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}
