package com.aquatic.lucre.core

/**
 * Adapter listener interface for
 * ensuring that the listener event
 * for recycler views is implemented
 */
interface AdapterListener<T> {
    fun onItemClick(item: T)
}
