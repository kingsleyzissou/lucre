package com.aquatic.lucre.viewmodels

import androidx.core.util.Predicate
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
            val predicate = Predicate<Category> { it.userId == auth.currentUser?.uid!! }
            store.subscribe(predicate).subscribe {
                list.postValue(it)
            }
        }
    }

    fun saveCategory(category: Category) {
        viewModelScope.launch {
            store.save(category)
        }
    }
}
