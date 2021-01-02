package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Entry
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import org.jetbrains.anko.AnkoLogger

/**
 * EntryStore for storing and retrieving
 * entry items. The store is saved to a
 * json file.
 */
class EntryStore(store: CollectionReference) : CRUDStore<Entry>(store), AnkoLogger {

    /**
     * List all the items for a given model
     */
    override suspend fun all(): List<Entry> {
        return store.get().await().map { it.toObject(Entry::class.java) }
    }

    /**
     * Specific function for filtering the EntryStore items
     * by a custom predicate. In the app, this is generally
     * by a date and a vault id
     */
    suspend fun where(key: String, value: Any): List<Entry> {
        return store.whereEqualTo(key, value).get().await().map { it.toObject(Entry::class.java) }
    }

    override suspend fun find(id: String): Entry? {
        return store.document(id).get().await().toObject(Entry::class.java)
    }
}
