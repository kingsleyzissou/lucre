package com.aquatic.lucre.main

import android.app.Application
import com.aquatic.lucre.models.User
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class App : Application(), AnkoLogger {
    var user: User? = null
    override fun onCreate() {
        super.onCreate()
        info { "Lucre App started" }
    }
}
