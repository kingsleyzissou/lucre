package com.aqautic.lucre.repositories

import com.aquatic.lucre.models.Category
import com.aquatic.lucre.repositories.CRUDStore
import com.google.firebase.firestore.CollectionReference

/**
 * CategoryStore for storing and retrieving
 * category items. The store is saved to a
 * json file.
 */
class CategoryStore(store: CollectionReference) : CRUDStore<Category>(store) {

    /**
     * List all the items for a given model
     */
    override fun all(): List<Category> {
        var list: List<Category> = ArrayList()
        store.get().addOnSuccessListener {
            list = it.map { it.toObject(Category::class.java) }
        }
        return list
    }

    /**
     * Since it is possible for a category to
     * be deleted and entries for that category to still exist,
     * we need to specify a fallback category
     */
    override fun find(id: String): Category? {
        var result: Category? = Category(
            "Uncategorized",
            "No category",
            "#FFFFFF"
        )
        store.document(id).get()
            .addOnSuccessListener {
                result = it.toObject(Category::class.java)
            }
        return result
    }
}
