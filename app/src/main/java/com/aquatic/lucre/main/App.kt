package com.aquatic.lucre.main

import android.app.Application
import com.aqautic.lucre.repositories.CategoryStore
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.repositories.EntryStore
import com.aquatic.lucre.repositories.VaultStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class App: Application(), AnkoLogger {

    val vaultStore = VaultStore()

    private val vault1 = Vault(
        "AIB",
        "Euro",
        "€"
    )

    private val vault2 = Vault(
        "US",
        "Dollar",
        "$"
    )

    private val vault3 = Vault(
        "HSBC",
        "Pound",
        "£"
    )

    override fun onCreate() {
        super.onCreate()
        vaultStore.create(vault1)
        vaultStore.create(vault2)
        vaultStore.create(vault3)
        info { "Lucre App started" }
    }
}