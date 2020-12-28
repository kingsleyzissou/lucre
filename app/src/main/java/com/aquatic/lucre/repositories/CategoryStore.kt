package com.aqautic.lucre.repositories

import com.aquatic.lucre.models.Category
import com.aquatic.lucre.repositories.CRUDStore
import com.aquatic.lucre.utilities.read
import javax.json.JsonObject

/**
 * CategoryStore for storing and retrieving
 * category items. The store is saved to a
 * json file.
 */
class CategoryStore(file: String = "categories.json") : CRUDStore<Category>(file) {

    /**
     * Since it is possible for a category to
     * be deleted and entries for that category to still exist,
     * we need to specify a fallback category
     */
    override fun find(id: String): Category? {
        if (list[id] != null) return list[id]
        return Category(
            "Uncategorized",
            "No category",
            "#FFFFFF"
        )
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
        val arr = contents.getJsonArray("list")
        // push the item to the CRUDStore list
        arr?.forEach {
            var model = Category()
            model.updateModel(it as JsonObject)
            list[model.id] = model
        }
    }
}
