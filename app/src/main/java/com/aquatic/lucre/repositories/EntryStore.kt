package org.wit.lucre.repositories

import org.wit.lucre.models.Entry
import org.wit.lucre.utilities.read
import tornadofx.jsonArray
import tornadofx.toModel
import java.util.function.Predicate
import javax.json.JsonObject

/**
 * EntryStore for storing and retrieving
 * entry items. The store is saved to a
 * json file.
 */
class EntryStore(file: String = "entries.json") : CRUDStore<Entry>(file) {

    /**
     * Specific function for filtering the EntryStore items
     * by a custom predicate. In the app, this is generally
     * by a date and a vault id
     */
    fun where(predicate: Predicate<Entry>): List<Entry> {
        return list
            .filter { predicate.test(it.value) }
            .values
            .toList()
    }

    /**
     * Custom deserialize method for the
     * category store to convert JSONObject
     * read from file into a list of category items
     */
    override fun deserialize() {
        // get the file contents
        val contents: JsonObject = read(filename)!!
        // convert the file contents to a model using `TornadoFX.toModel` helper
        val arr = contents.jsonArray("list")?.toModel<Entry>()
        // push the item to the CRUDStore list
        arr?.forEach { list[it.id] = it }
    }
}
