package com.aquatic.lucre.repositories

import androidx.core.util.Predicate
import com.aquatic.lucre.models.Model
import com.google.firebase.firestore.CollectionReference
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.tasks.await
import org.jetbrains.anko.AnkoLogger

/**
 * Genereic CRUDStore class. This class
 * contains all the shared methods of the
 * various CRUDStores to avoid code duplication
 */
abstract class CRUDStore<T : Model>(var store: CollectionReference) : CRUDStoreInterface<T>, AnkoLogger {

    /**
     * List all the items for a given model
     */
    abstract override suspend fun all(): List<T>

    /**
     * Find the given model by id
     */
    abstract override suspend fun find(id: String): T?

    /**
     * Create or update a model
     */
    override suspend fun save(value: T) {
        try {
            store.document(value.id!!).set(value).await()
        } catch (e: Exception) {
            error("Unable to save: ${e.message}")
        }
    }

    /**
     * Delete an item and save the
     */
    override suspend fun delete(value: T) {
        try {
            value.deleted = true
            store.document(value.id!!).set(value).await()
        } catch (e: Exception) {
            error("Unable to delete: ${e.message}")
        }
    }

    /**
     * Add multiple items to the store list
     */
    override suspend fun addAll(values: List<T>) {
        values.forEach { v -> this.save(v) }
    }

    abstract override fun subscribe(predicate: Predicate<T>?): Observable<List<T>>
}
