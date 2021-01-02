package com.aquatic.lucre.repositories

import androidx.core.util.Predicate
import com.aquatic.lucre.models.Vault
import com.google.firebase.firestore.CollectionReference
import io.reactivex.rxjava3.core.Observable
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

    /**
     * Specific function for filtering the Vault items
     * by a custom predicate. In the app, this is generally
     * by a date and a vault id
     */
    suspend fun where(key: String, value: Any): List<Vault> {
        return store.whereEqualTo(key, value).get().await().map { it.toObject(Vault::class.java) }
    }

    override suspend fun find(id: String): Vault? {
        return store.document(id).get().await().toObject(Vault::class.java)
    }

    override fun subscribe(predicate: Predicate<Vault>?): Observable<List<Vault>> {
        return Observable.create {
            store.addSnapshotListener { snapshot, _ ->
                val vaults = snapshot?.toObjects(Vault::class.java)?.filter {
                    if (predicate == null) true else predicate.test(it)
                }
                it.onNext(vaults)
            }
        }
    }
}
