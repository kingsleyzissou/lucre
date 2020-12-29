package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Model
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Genereic CRUDStore class. This class
 * contains all the shared methods of the
 * various CRUDStores to avoid code duplication
 */
abstract class CRUDStore<T : Model>(var filename: String) : CRUDStoreInterface<T>, AnkoLogger {

    /**
     * CRUDStore items are stored in a id/value
     * map, so each element can easily be accessed
     * by the id
     */
    var list: HashMap<String, T> = HashMap()

    /**
     * List all the items for a given model
     */
    override fun all(): List<T> {
        return list.values.toList()
    }

    /**
     * Find the given model by id
     */
    override fun find(id: String): T? {
        return list[id]
    }

    /**
     * Create a model and save to file
     */
    override fun create(value: T) {
        list[value.id] = value
        logAll()
    }

    /**
     * Update a model and save results
     * to file
     */
    override fun update(value: T) {
        val model = find(value.id)
        if (model != null) {
            list[value.id] = value
        }
    }

    /**
     * Delete an item and save the
     */
    override fun delete(id: String) {
        list.remove(id)
    }

    /**
     * Add multiple items to the store list
     */
    override fun addAll(values: List<T>) {
        values.forEach { v -> this.create(v) }
    }

    fun logAll() {
        list.forEach { info("Lucre-vault: $it") }
    }

    /**
     * Deserialize method to be
     * implemented by each CRUDStore
     */
    abstract fun deserialize()
}
