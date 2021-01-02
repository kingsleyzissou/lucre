package com.aqautic.lucre.repositories

import com.aquatic.lucre.models.Category
import com.aquatic.lucre.repositories.CRUDStore
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await

/**
 * CategoryStore for storing and retrieving
 * category items. The store is saved to a
 * json file.
 */
class CategoryStore(store: CollectionReference) : CRUDStore<Category>(store) {

    /**
     * List all the items for a given model
     */
    override suspend fun all(): List<Category> {
        return store.get().await().map { it.toObject(Category::class.java) }
    }

    /**
     * Since it is possible for a category to
     * be deleted and entries for that category to still exist,
     * we need to specify a fallback category
     */
    override suspend fun find(id: String): Category? {
        return store.document(id).get()
            .await()
            .toObject(Category::class.java)
            ?: Category(
                "Uncategorized",
                "No category",
                "#FFFFFF"
            )
    }
}
