package com.aquatic.lucre.viewmodels

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
            collection.addSnapshotListener{ snapshot, _ ->
                var entry = snapshot?.toObjects(Entry::class.java)
                list.postValue(entry)
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
