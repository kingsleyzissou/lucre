package com.aquatic.lucre.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aquatic.lucre.R
import com.aquatic.lucre.main.App
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var app: App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as App

        val nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val controller = findNavController(R.id.fragment)

        // reference
        // youtube.com/watch?v=Chso6xrJ6aU
        setupActionBarWithNavController(
            controller,
            AppBarConfiguration(
                setOf(
                    R.id.vaultListFragment,
                    R.id.entryListFragment,
                    R.id.categoryListFragment
                )
            )
        )

        nav.setupWithNavController(controller)
    }
}
