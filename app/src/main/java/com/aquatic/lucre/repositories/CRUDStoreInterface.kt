package com.aquatic.lucre.repositories

/**
 * CRUDStore interface for
 * ensuring that all the required
 * methods are implemented
 */
interface CRUDStoreInterface<T> {
    suspend fun all(): List<T>
    suspend fun find(id: String): T?
    suspend fun save(value: T)
    suspend fun delete(id: String)
    suspend fun addAll(values: List<T>)
}
