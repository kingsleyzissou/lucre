package com.aquatic.lucre.viewmodels

import androidx.core.util.Predicate
import androidx.lifecycle.viewModelScope
import com.aqautic.lucre.repositories.CategoryStore
import com.aquatic.lucre.models.Category
import kotlinx.coroutines.launch

class CategoryViewModel : BaseViewModel<Category>() {

    private val collection = firestore.collection("categories")
    private val store = CategoryStore(collection)

    val uncategorised = Category(
        "Uncategorised",
        "None",
        "#FFFFFF",
        deleted = false
    )

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val categories = store.all()
            bufferList(categories)
            val predicate = Predicate<Category> { it.userId == auth.currentUser?.uid!! }
            store.subscribe(predicate).subscribe {
                bufferList(it)
            }
        }
    }

    private fun bufferList(categories: List<Category>) {
        var l = categories.filter { it.deleted == false }
        if (l.isEmpty()) {
            list.postValue(listOf(uncategorised))
            return
        }
        list.postValue(l)
    }

    fun saveCategory(category: Category) {
        viewModelScope.launch {
            store.save(category)
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            store.delete(category)
        }
    }

    fun find(predicate: Predicate<Category>, haystack: List<Category>): Category {
        return haystack.find { predicate.test(it) } ?: uncategorised
    }
}
