package com.aquatic.lucre.viewmodels

import androidx.core.util.Predicate
import androidx.lifecycle.viewModelScope
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.repositories.EntryStore
import kotlinx.coroutines.launch
import org.jetbrains.anko.info

class EntryViewModel : BaseViewModel<Entry>() {

    private val collection = firestore.collection("entries")
    private val store = EntryStore(collection)

    init {
        getEntries()
    }

    private fun getEntries() {
        viewModelScope.launch {
            val entries = store.all()
            list.postValue(entries)
            val predicate = Predicate<Entry> { it.userId == auth.currentUser?.uid!! }
            store.subscribe(predicate).subscribe {
                list.postValue(it)
            }
        }
    }

    fun saveEntry(entry: Entry) {
        viewModelScope.launch {
            info("Initialising save")
            store.save(entry)
        }
    }
}
