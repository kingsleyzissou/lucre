package com.aquatic.lucre.viewmodels

import androidx.lifecycle.viewModelScope
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.repositories.VaultStore
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger

class VaultViewModel : BaseViewModel<Vault>(), AnkoLogger {

    private val collection = firestore.collection("vaults")
    private val store = VaultStore(collection)

    init {
        getVaults()
    }

    private fun getVaults() {
        viewModelScope.launch {
            var vaults = store.all()
            list.postValue(vaults)
            // TODO create an observable in repository
            collection.addSnapshotListener { snapshot, _ ->
                val vault = snapshot?.toObjects(Vault::class.java)
                list.postValue(vault)
            }
        }
    }

    fun saveVault(vault: Vault) {
        viewModelScope.launch {
            store.save(vault)
        }
    }
}
