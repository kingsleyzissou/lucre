package com.aquatic.lucre.core

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Genereic BaseAdapter class. This class
 * contains all the shared methods of the
 * various Adapters to avoid code duplication
 */
abstract class BaseAdapter<T> constructor(
    var list: MutableList<T>,
    private val listener: AdapterListener<T>
) : RecyclerView.Adapter<BaseAdapter.MainHolder<T>>() {

    /**
     * The implementation for each adapter is
     * slightly different and this is left to
     * the concrete classes
     */
    abstract override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder<T>

    /**
     * This method is shared in the same exact format in
     * all the adapters and binds the list and listener
     * to the underlying view holder
     */
    override fun onBindViewHolder(holder: MainHolder<T>, position: Int) {
        holder.bind(list[position], listener)
    }

    /**
     * This method is shared in the same exact format in
     * all the adapters
     */
    override fun getItemCount(): Int = list.size

    /**
     * Abstract inner class for binding an individual item
     * to the recycler view.
     */
    abstract class MainHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(value: T, listener: AdapterListener<T>)
    }
}
