package com.aquatic.lucre.activities.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.aquatic.lucre.R
import com.aquatic.lucre.adapters.ChartCategoryAdapter
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.core.BaseListFragment
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.viewmodels.CategoryViewModel
import kotlinx.android.synthetic.main.fragment_chart_category_list.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ChartCategoryFragment : BaseListFragment<Category>(), AdapterListener<Category>, AnkoLogger {

    override var list: MutableList<Category> = ArrayList()
    override var adapter = ChartCategoryAdapter(list, this) as BaseAdapter<Category>
    override val model: CategoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_chart_category_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.chartCategoriees.layoutManager = LinearLayoutManager(context)
        view.chartCategoriees.adapter = adapter
        observeStore()
    }

    override fun observeStore() {
        model.list.observe(
            viewLifecycleOwner,
            Observer {
                info("We got here: $it")
                adapter.list.clear()
                adapter.list.addAll(it)
                adapter.notifyDataSetChanged()
            }
        )
    }

    override fun onItemClick(item: Category) {}
}
