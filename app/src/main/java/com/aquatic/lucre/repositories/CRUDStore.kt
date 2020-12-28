package org.wit.lucre.repositories

import org.wit.lucre.models.Model
import org.wit.lucre.utilities.fileExists
import org.wit.lucre.utilities.write
import javax.json.Json

/**
 * Genereic CRUDStore class. This class
 * contains all the shared methods of the
 * various CRUDStores to avoid code duplication
 */
abstract class CRUDStore<T : Model>(var filename: String) : CRUDStoreInterface<T> {

    /**
     * CRUDStore items are stored in a id/value
     * map, so each element can easily be accessed
     * by the id
     */
    var list: HashMap<String, T> = HashMap()

    /**
     * Deserialize JSON file on startup
     */
    init {
        if (fileExists(filename)) {
            deserialize()
        }
    }

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
        serialize()
    }

    /**
     * Update a model and save results
     * to file
     */
    override fun update(value: T) {
        val model = find(value.id)
        if (model != null) {
            list[value.id] = value
            serialize()
        }
    }

    /**
     * Delete an item and save the
     */
    override fun delete(id: String) {
        list.remove(id)
        serialize()
    }

    /**
     * Add multiple items to the store list
     */
    override fun addAll(values: List<T>) {
        values.forEach { v -> this.create(v) }
    }

    /**
     * Serialize the store item to a JSON
     * object that will then be written
     * to a file
     */
    fun serialize() {
        var jsonObject = Json.createObjectBuilder()
        var jsonArray = Json.createArrayBuilder()
        all().map { jsonArray.add(it.toJSON()) }
        jsonObject.add("list", jsonArray)
        write(filename, jsonObject.build().toString())
    }

    /**
     * Deserialize method to be
     * implemented by each CRUDStore
     */
    abstract fun deserialize()
}
