package com.aquatic.lucre.viewmodels

import androidx.core.util.Predicate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.repositories.EntryStore
import kotlinx.coroutines.launch
import org.jetbrains.anko.info

class EntryViewModel : BaseViewModel<Entry>() {

    var balance: MutableLiveData<Float> = MutableLiveData(0F)

    private val collection = firestore.collection("entries")
    private val store = EntryStore(collection)

    fun getEntries(vault: String? = null) {
        info("Vault id: $vault")
        viewModelScope.launch {
            val entries = if (vault == null) store.all() else store.where("vault", vault)
            list.postValue(entries)
            balance(entries)
            val predicate = if (vault == null) null else Predicate<Entry> { it.vault == vault }
            store.subscribe(predicate).subscribe {
                list.postValue(it)
                balance(it)
            }
        }
    }

    fun balance(entries: List<Entry>) {
        if (!entries.isEmpty()) {
            val b = entries
                // get a list of the positive/negative values
                .map { it.signedAmount }
                // sum the amount
                .reduce { acc, it -> acc + it }
            balance.postValue(b)
            return
        }
        balance.postValue(0f)
    }

    fun saveEntry(entry: Entry) {
        viewModelScope.launch {
            info("Initialising save")
            store.save(entry)
        }
    }
}
