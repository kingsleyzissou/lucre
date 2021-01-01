package com.aquatic.lucre.repositories

/**
 * CRUDStore interface for
 * ensuring that all the required
 * methods are implemented
 */
interface CRUDStoreInterface<T> {
    fun all(): List<T>
    fun find(id: String): T?
    fun save(value: T)
    fun delete(id: String)
    fun addAll(values: List<T>)
}
