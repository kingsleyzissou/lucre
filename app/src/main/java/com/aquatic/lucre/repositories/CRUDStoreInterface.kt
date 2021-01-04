package com.aquatic.lucre.repositories

import androidx.core.util.Predicate
import io.reactivex.rxjava3.core.Observable

/**
 * CRUDStore interface for
 * ensuring that all the required
 * methods are implemented
 */
interface CRUDStoreInterface<T> {
    suspend fun all(): List<T>
    suspend fun find(id: String): T?
    suspend fun save(value: T)
    suspend fun delete(value: T)
    suspend fun addAll(values: List<T>)
    fun subscribe(predicate: Predicate<T>? = null): Observable<List<T>>
}
