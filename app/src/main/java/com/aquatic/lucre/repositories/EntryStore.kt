package com.aquatic.lucre.repositories

import com.aquatic.lucre.models.Entry
import com.google.firebase.firestore.CollectionReference
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
    override fun all(): List<Entry> {
        var list: List<Entry> = ArrayList()
        store.get().addOnSuccessListener {
            list = it.map { it.toObject(Entry::class.java) }
        }
        return list
    }

    /**
     * Specific function for filtering the EntryStore items
     * by a custom predicate. In the app, this is generally
     * by a date and a vault id
     */
    fun where(key: String, value: Any): List<Entry> {
        var list: List<Entry> = ArrayList()
        store.whereEqualTo(key, value).get().addOnSuccessListener {
            list = it.map { it.toObject(Entry::class.java) }
        }
        return ArrayList<Entry>()
    }

    override fun find(id: String): Entry? {
        var result: Entry? = null
        store.document(id).get()
            .addOnSuccessListener {
                result = it.toObject(Entry::class.java)
            }
        return result
    }
}
