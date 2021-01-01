package com.aquatic.lucre.main

import android.app.Application
import com.aqautic.lucre.repositories.CategoryStore
import com.aquatic.lucre.models.Category
import com.aquatic.lucre.models.User
import com.aquatic.lucre.models.Vault
import com.aquatic.lucre.repositories.EntryStore
import com.aquatic.lucre.repositories.VaultStore
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class App : Application(), AnkoLogger {

    var user: User? = null
    lateinit var firestore: FirebaseFirestore
    lateinit var vaultStore: VaultStore
    lateinit var categoryStore: CategoryStore
    lateinit var entryStore: EntryStore

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

    private val c1 = Category(
        "Bills",
        ":(",
        "#FFFFFF"
    )

    private val c2 = Category(
        "Rent",
        ":(",
        "#FF0000"
    )

    private val c3 = Category(
        "Salary",
        ":)",
        "#0000FF"
    )

    override fun onCreate() {
        super.onCreate()
        firestore = FirebaseFirestore.getInstance()
        vaultStore = VaultStore(firestore.collection("vaults"))
        categoryStore = CategoryStore(firestore.collection("categories"))
        entryStore = EntryStore(firestore.collection("entries"))
//        vaultStore.addAll(listOf(vault1, vault2, vault3))
//        categoryStore.addAll(listOf(c1, c2, c3))
//        entryStore.create(
//            Entry(
//                20f,
//                EntryType.EXPENSE,
//                "Vodafone",
//                "Phone bill",
//                c1,
//                vault2.id
//            )
//        )
        info { "Lucre App started" }
    }

}
