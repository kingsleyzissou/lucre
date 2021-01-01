package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Vault
import com.google.firebase.firestore.CollectionReference

/**
 * VaultStore for storing and retrieving
 * vault items. The store is saved to a
 * json file.
 */
class VaultStore(store: CollectionReference) : CRUDStore<Vault>(store) {

    override fun all(): List<Vault> {
        var list: List<Vault> = ArrayList()
        store.get()
            .addOnSuccessListener {
                list = it.map { it.toObject(Vault::class.java) }
            }
        return list
    }

    override fun find(id: String): Vault? {
        var result: Vault? = null
        store.document(id).get()
            .addOnSuccessListener {
                result = it.toObject(Vault::class.java)
            }
        return result
    }
}
