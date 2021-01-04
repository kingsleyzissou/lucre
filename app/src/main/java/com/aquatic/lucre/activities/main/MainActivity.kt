package com.aquatic.lucre.activities.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.auth.LoginActivity
import com.aquatic.lucre.main.App
import com.aquatic.lucre.models.User
import com.aquatic.lucre.viewmodels.UserViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    /* Android application object */
    lateinit var app: App

    /* Session user */
    var user: User? = User()

    /* Inject User View Model */
    val model: UserViewModel by viewModels()

    /**
     * Set up the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as App

        val nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val controller = findNavController(R.id.fragment)
        observeLoginFlow()
        handleIntent()

        // Reference for bottom nav:
        // youtube.com/watch?v=Chso6xrJ6aU
        setupActionBarWithNavController(
            controller,
            AppBarConfiguration(
                setOf(
                    R.id.dashboardFragment,
                    R.id.entryListFragment,
                    R.id.categoryListFragment
                )
            )
        )

        nav.setupWithNavController(controller)
    }

    /**
     * Handle the intent and set
     * the session user
     */
    private fun handleIntent() {
        if (intent.hasExtra("user")) {
            user = intent.extras?.getParcelable<User>("user")!!
        }
    }

    /**
     * Function for logging out user and returning
     * to the login screen
     */
    private fun logout() {
        model.logout()
    }

    /**
     * Subscribe to the login datastate in order
     * to display errors, loading state or handle response
     * data
     */
    private fun observeLoginFlow() {
        model.datastate.observe(
            this,
            Observer {
//                displayProgressBar(it.loading)
                if (!it.message.isNullOrEmpty()) toast("${it.message}")
                if (it.data != null) {
                    user = null
                    startActivity(intentFor<LoginActivity>())
                }
            }
        )
    }

    /**
     * Add the cancel options menu for the activity
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Return to previous activity if activity is
     * cancelled
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.entry_logout -> {
                logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
