package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.utilities.read
import javax.json.JsonObject

/**
 * VaultStore for storing and retrieving
 * vault items. The store is saved to a
 * json file.
 */
class VaultStore(file: String = "vaults.json") : CRUDStore<Vault>(file) {

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
            var model = Vault()
            model.updateModel(it as JsonObject)
            list[model.id] = model
        }
    }
}
