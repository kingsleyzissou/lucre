package com.aquatic.lucre.activities.fragments

import android.content.Intent
import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.CategoryActivity
import com.aquatic.lucre.adapters.AdapterListener
import com.aquatic.lucre.adapters.BaseAdapter
import com.aquatic.lucre.adapters.CategoryAdapter
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_category_list.view.*
import kotlinx.android.synthetic.main.fragment_entry_list.view.*
import org.jetbrains.anko.intentFor

class CategoryListFragment : BaseListFragment<Category>(), AdapterListener<Category> {

    override var list: MutableList<Category> = ArrayList()
    override var adapter = CategoryAdapter(list, this) as BaseAdapter<Category>
    override val model: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_category_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        view.categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        view.categoryRecyclerView.adapter = adapter
        observeStore()
    }

    override fun observeStore() {
        model.list.observe(
            viewLifecycleOwner,
            Observer {
                adapter.list.clear()
                adapter.list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        var ctx = this
//        runBlocking {
//            var list = app.categoryStore.all()
//            view?.categoryRecyclerView?.adapter = CategoryAdapter(
//                list,
//                ctx
//            )
//            super.onActivityResult(requestCode, resultCode, data)
//        }
    }

    override fun onCardClick(item: Category) {
        startActivityForResult(
            context?.intentFor<CategoryActivity>()?.putExtra("category_edit", item),
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
