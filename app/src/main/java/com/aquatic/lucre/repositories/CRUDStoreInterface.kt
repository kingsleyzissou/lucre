package org.wit.lucre.repositories

/**
 * CRUDStore interface for
 * ensuring that all the required
 * methods are implemented
 */
interface CRUDStoreInterface<T> {
    fun all(): List<T>
    fun find(id: String): T?
    fun create(value: T)
    fun update(value: T)
    fun delete(id: String)
    fun addAll(values: List<T>)
}
