package com.aquatic.lucre.viewmodels

import androidx.lifecycle.viewModelScope
import com.aqautic.lucre.repositories.CategoryStore
import com.aquatic.lucre.models.Category
import kotlinx.coroutines.launch

class CategoryViewModel : BaseViewModel<Category>() {

    private val collection = firestore.collection("categories")
    private val store = CategoryStore(collection)

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            val categories = store.all()
            list.postValue(categories)
            collection.addSnapshotListener { snapshot, _ ->
                var category = snapshot?.toObjects(Category::class.java)
                list.postValue(category)
            }
        }
    }

    fun saveCategory(category: Category) {
        viewModelScope.launch {
            store.save(category)
        }
    }
}
