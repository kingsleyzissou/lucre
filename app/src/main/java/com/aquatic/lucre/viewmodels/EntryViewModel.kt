package com.aquatic.lucre.viewmodels

import androidx.core.util.Predicate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.models.Entry
import com.aquatic.lucre.repositories.EntryStore
import kotlinx.coroutines.launch
import org.jetbrains.anko.info

class EntryViewModel : BaseViewModel<Entry>() {

    var balance: MutableLiveData<Float> = MutableLiveData(0F)

    private val collection = firestore.collection("entries")
    private val store = EntryStore(collection)

    val uncategorised = Category(
        "Uncategorised",
        "None",
        "#FF0000"
    )

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

    /**
     * The expense categories is used to break the
     * expenses up into their different categories
     * to then be displayed as a pie chart
     */
    fun expenseCategories(entries: List<Entry>, categories: List<Category>): Map<Category, Float> {
        // validation
        if (entries.isEmpty()) return emptyMap()
        // filter the entries by expenses
        val predicate = Predicate<Entry> { it.type == "EXPENSE" }
        return entries.filter { predicate.test(it) }
            // group expenses by category id
            .groupBy { it.category }
            // sum the amounts
            .mapValues { (_, value) ->
                value
                    // return a list of amount as a Double
                    .map { it.amount!! }
                    // sum the expenses in the category
                    .reduce { acc, it -> acc + it }
            }
            // convert the keys from id back to category
            .mapKeys {
                categories.find { category -> (category.id == it.key) } ?: uncategorised
            }
    }

    fun saveEntry(entry: Entry) {
        viewModelScope.launch {
            info("Initialising save")
            store.save(entry)
        }
    }
}
