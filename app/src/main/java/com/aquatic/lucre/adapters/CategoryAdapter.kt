package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aquatic.lucre.R
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.models.Category
import kotlinx.android.synthetic.main.recycler_category_list_item.view.*

class CategoryAdapter constructor(
    categories: MutableList<Category>,
    listener: AdapterListener<Category>
) : BaseAdapter<Category>(categories, listener) {

    /**
     * The implementation for each adapter is
     * slightly different and this is left to
     * the concrete classes
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.MainHolder {
        return CategoryAdapter.MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_category_list_item,
                parent,
                false
            )
        )
    }

    /**
     * View holder for the bottom sheet recycler view
     */
    class MainHolder constructor(itemView: View) : BaseAdapter.MainHolder<Category>(itemView) {
        override fun bind(value: Category, listener: AdapterListener<Category>) {
            itemView.categoryTitle.text = value.name
            itemView.categoryDescription.text = value.description
            itemView.setOnClickListener { listener.onItemClick(value) }
        }
    }
}
