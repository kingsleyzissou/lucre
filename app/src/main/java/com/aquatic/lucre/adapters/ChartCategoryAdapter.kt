package com.aquatic.lucre.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aquatic.lucre.R
import com.aquatic.lucre.core.AdapterListener
import com.aquatic.lucre.core.BaseAdapter
import com.aquatic.lucre.models.Category
import kotlinx.android.synthetic.main.recycler_chart_category_item.view.*

class ChartCategoryAdapter constructor(
    categories: MutableList<Category>,
    listener: AdapterListener<Category>
) : BaseAdapter<Category>(categories, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartCategoryAdapter.MainHolder {
        return ChartCategoryAdapter.MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.recycler_chart_category_item,
                parent,
                false
            )
        )
    }

    class MainHolder constructor(itemView: View) : BaseAdapter.MainHolder<Category>(itemView) {
        override fun bind(value: Category, listener: AdapterListener<Category>) {
            itemView.chartLabel.text = value.name
            itemView.chartColor.setBackgroundColor(Color.parseColor(value.color))
        }
    }
}
