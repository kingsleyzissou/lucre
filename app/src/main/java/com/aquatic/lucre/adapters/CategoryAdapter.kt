package com.aquatic.lucre.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aquatic.lucre.R
import com.aquatic.lucre.models.Category
import kotlinx.android.synthetic.main.card_category.view.*

class CategoryAdapter constructor(
    private var categories: List<Category>,
    private val listener: AdapterListener<Category>
) : RecyclerView.Adapter<CategoryAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.MainHolder {
        return CategoryAdapter.MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_category,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, listener)
    }

    override fun getItemCount(): Int = categories.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category, listener: AdapterListener<Category>) {
            itemView.categoryTitle.text = category.name
            itemView.categoryDescription.text = category.description
            itemView.setOnClickListener { listener.onCardClick(category) }
        }
    }
}
