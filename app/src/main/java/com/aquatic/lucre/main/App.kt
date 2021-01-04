package com.aquatic.lucre.main

import android.app.Application
import com.aquatic.lucre.models.User
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

/**
 * Android Application object
 *
 * This is the entry point for the application and is referenced
 * else where in the application.
 */
class App : Application(), AnkoLogger {
    var user: User? = null
    override fun onCreate() {
        super.onCreate()
        info { "Lucre App started" }
    }
}
