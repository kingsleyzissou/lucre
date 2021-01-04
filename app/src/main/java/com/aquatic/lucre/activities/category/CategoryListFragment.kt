package com.aquatic.lucre.activities.category

import android.os.Bundle
import android.view.* // ktlint-disable no-wildcard-imports
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.CategoryAdapter
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.core.BaseListFragment
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_category_list.*
import kotlinx.android.synthetic.main.fragment_category_list.view.*
import org.jetbrains.anko.intentFor

class CategoryListFragment : BaseListFragment<Category>(), AdapterListener<Category> {

    override var list: MutableList<Category> = ArrayList()
    override var adapter = CategoryAdapter(list, this) as BaseAdapter<Category>
    override val model: CategoryViewModel by activityViewModels()

    /**
     * Inflate the view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_category_list, container, false)

    /**
     * Setup the view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.categoryRecyclerView.layoutManager = LinearLayoutManager(context)
        view.categoryRecyclerView.adapter = adapter
        floatingActionButton.setOnClickListener { switchActivity() }
        observeStore()
    }

    /**
     * Observe the category list live data
     * and update the recycler view if there
     * are any changes
     */
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

    /**
     * Go to the create category activity
     */
    private fun switchActivity() {
        startActivityForResult(
            context?.intentFor<CategoryActivity>(),
            0
        )
    }

    /**
     * Event listener for when a category item is pressed.
     * This click event takes the user to the category
     * edit activity
     */
    override fun onItemClick(item: Category) {
        startActivityForResult(
            context?.intentFor<CategoryActivity>()?.putExtra("category_edit", item),
            0
        )
    }
}
