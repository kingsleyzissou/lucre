package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Category
import kotlinx.android.synthetic.main.card_category.view.*

class CategoryAdapter constructor(
    categories: MutableList<Category>,
    listener: AdapterListener<Category>
) : BaseAdapter<Category>(categories, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.MainHolder {
        return CategoryAdapter.MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_category,
                parent,
                false
            )
        )
    }

    class MainHolder constructor(itemView: View) : BaseAdapter.MainHolder<Category>(itemView) {
        override fun bind(value: Category, listener: AdapterListener<Category>) {
            itemView.categoryTitle.text = value.name
            itemView.categoryTitle.text = value.name
            itemView.categoryTitle.text = value.name
        }
    }
}
