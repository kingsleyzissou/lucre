package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Model
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

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
        info("Saving item: $value")

        store.document(value.id!!).set(value)
            .addOnSuccessListener { info("Item added: $value") }
            .addOnFailureListener { error("Unable to save: ${it.message}") }
    }

    /**
     * Delete an item and save the
     */
    override suspend fun delete(id: String) {
        try {
            store.document(id).delete().await()
        } catch (e: Exception) {
            info("Unable to delete: ${e.message}")
        }
    }

    /**
     * Add multiple items to the store list
     */
    override suspend fun addAll(values: List<T>) {
        values.forEach { v -> this.save(v) }
    }
}
