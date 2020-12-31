package com.aquatic.lucre.repositories

import android.content.Context
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.utilities.read
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.function.Predicate
import javax.json.JsonObject

/**
 * EntryStore for storing and retrieving
 * entry items. The store is saved to a
 * json file.
 */
class EntryStore(context: Context, file: String = "entries.json") : CRUDStore<Entry>(context, file) {

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
        val contents: JsonObject = read(context, filename)!!
        // convert the file contents to a model using `TornadoFX.toModel` helper
        val arr = contents.getJsonArray("list")
        // push the item to the CRUDStore list
        info("Deserialize: $list")
        arr?.forEach {
            var model = Entry()
            model.updateModel(it as JsonObject)
            list[model.id] = model
        }
    }
}
