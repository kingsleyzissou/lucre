package com.aquatic.lucre.viewmodels

import androidx.core.util.Predicate
import androidx.lifecycle.viewModelScope
import com.aqautic.lucre.repositories.CategoryStore
import com.aquatic.lucre.core.BaseViewModel
import com.aquatic.lucre.models.Category
import kotlinx.coroutines.launch

class CategoryViewModel : BaseViewModel<Category>() {

    /* The collection reference for the store */
    private val collection = firestore.collection("categories")

    /* The firebase collection */
    private val store = CategoryStore(collection)

    /* Default category used to avoid bugs */
    val uncategorised = Category(
        "Uncategorised",
        "None",
        "#FFFFFF",
        deleted = false
    )

    /**
     * Fetch all categories on init
     */
    init {
        getCategories()
    }

    /**
     * Use a coroutine inside the viewmodel scope
     * to fetch all the categories from firebase
     *
     * // TODO datastate
     */
    private fun getCategories() {
        viewModelScope.launch {
            val categories = store.all()
            bufferList(categories)
            val predicate = Predicate<Category> { it.userId == auth.currentUser?.uid!! }
            // subscribe to live updates from firestore
            store.subscribe(predicate).subscribe {
                bufferList(it)
            }
        }
    }

    /**
     * Helper method to ensure that an empty
     * list is not returned to the views
     * as this causes unwanted behaviour
     */
    private fun bufferList(categories: List<Category>) {
        val l = categories.filter { it.deleted == false }
        if (l.isEmpty()) {
            list.postValue(listOf(uncategorised))
            return
        }
        list.postValue(l)
    }

    /**
     * Save the category to firebase using
     * a coroutine
     */
    fun saveCategory(category: Category) {
        viewModelScope.launch {
            store.save(category)
        }
    }

    /**
     * Soft delete the category from firebase using
     * a coroutine
     */
    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            store.delete(category)
        }
    }

    /**
     * Helper function for finding a category using predicates
     */
    fun find(predicate: Predicate<Category>, haystack: List<Category>): Category {
        return haystack.find { predicate.test(it) } ?: uncategorised
    }
}
