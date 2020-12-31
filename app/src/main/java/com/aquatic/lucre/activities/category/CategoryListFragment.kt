package com.aquatic.lucre.activities.category

import android.content.Intent
import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.CategoryAdapter
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.Category
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_category_list.view.*
import org.jetbrains.anko.intentFor

class CategoryListFragment : Fragment(), AdapterListener<Category> {

    lateinit var app: App

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_category_list, container, false)
        setHasOptionsMenu(true)

        app = context?.applicationContext as App

        view.categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        view.categoryRecyclerView.adapter = CategoryAdapter(app.categoryStore.all(), this)

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        categoryRecyclerView.adapter = CategoryAdapter(app.categoryStore.all(), this)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onCardClick(category: Category) {
        startActivityForResult(
            context?.intentFor<CategoryActivity>()?.putExtra("category_edit", category),
            0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_category_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category_add -> startActivityForResult(
                context?.intentFor<CategoryActivity>(),
                0
            )
        }
        return super.onOptionsItemSelected(item)
    }
}
