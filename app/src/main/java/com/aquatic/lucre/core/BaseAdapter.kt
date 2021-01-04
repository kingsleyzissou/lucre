package com.aquatic.lucre.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T> constructor(
    var list: MutableList<T>,
    private val listener: AdapterListener<T>
) : RecyclerView.Adapter<BaseAdapter.MainHolder<T>>() {

    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder<T>

    override fun onBindViewHolder(holder: MainHolder<T>, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount(): Int = list.size

    abstract class MainHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(value: T, listener: AdapterListener<T>)
    }
}
