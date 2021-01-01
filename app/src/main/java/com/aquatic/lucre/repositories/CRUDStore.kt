package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Model
import com.google.firebase.firestore.CollectionReference
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
    abstract override fun all(): List<T>

    /**
     * Find the given model by id
     */
    abstract override fun find(id: String): T?

    /**
     * Create or update a model
     */
    override fun save(value: T) {
        store.document(value.id!!).set(value)
            .addOnCompleteListener { info("Document added successfully") }
    }

    /**
     * Delete an item and save the
     */
    override fun delete(id: String) {
        store.document(id).delete()
            .addOnCompleteListener { info("Document deleted successfully") }
    }

    /**
     * Add multiple items to the store list
     */
    override fun addAll(values: List<T>) {
        values.forEach { v -> this.save(v) }
    }
}
