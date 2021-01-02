package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Vault
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

/**
 * VaultStore for storing and retrieving
 * vault items. The store is saved to a
 * json file.
 */
class VaultStore(store: CollectionReference) : CRUDStore<Vault>(store) {

    override suspend fun all(): List<Vault> {
        return store.get().await().map { it.toObject(Vault::class.java) }
    }

    override suspend fun find(id: String): Vault? {
        return store.document(id).get().await().toObject(Vault::class.java)
    }
}
