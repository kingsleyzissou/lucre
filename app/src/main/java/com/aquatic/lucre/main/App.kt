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
    override fun onCreate() {
        super.onCreate()
        info { "Lucre App started" }
    }
}
