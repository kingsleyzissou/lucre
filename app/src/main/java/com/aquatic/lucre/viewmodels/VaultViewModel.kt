package com.aquatic.lucre.viewmodels

import androidx.core.util.Predicate
import androidx.lifecycle.viewModelScope
import com.aquatic.lucre.core.BaseViewModel
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
            val vaults = store.where("userId", auth.currentUser?.uid!!)
            val predicate = Predicate<Vault> { it.userId == auth.currentUser?.uid!! && it.deleted == false }
            list.postValue(vaults.filter { predicate.test(it) })
            store.subscribe(predicate).subscribe {
                list.postValue(it)
            }
        }
    }

    fun saveVault(vault: Vault) {
        viewModelScope.launch {
            store.save(vault)
        }
    }

    fun deleteVault(vault: Vault) {
        viewModelScope.launch {
            store.delete(vault)
        }
    }
}
