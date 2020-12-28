package org.wit.lucre.repositories

import javafx.scene.paint.Color
import org.wit.lucre.models.Category
import org.wit.lucre.utilities.read
import tornadofx.jsonArray
import tornadofx.toModel
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
            Color.web("#EEEEEE")
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
        val arr = contents.jsonArray("list")?.toModel<Category>()
        // push the item to the CRUDStore list
        arr?.forEach { list[it.id] = it }
    }
}
