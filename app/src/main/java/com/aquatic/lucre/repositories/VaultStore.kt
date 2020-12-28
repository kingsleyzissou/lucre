package org.wit.lucre.repositories

import org.wit.lucre.models.Vault
import org.wit.lucre.utilities.read
import tornadofx.jsonArray
import tornadofx.toModel
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
        val arr = contents.jsonArray("list")?.toModel<Vault>()
        // push the item to the CRUDStore list
        arr?.forEach { list[it.id] = it }
    }
}
