package com.aquatic.lucre.activities.auth

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.main.MainActivity
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_login.username
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), AnkoLogger {

    /* Get the User ViewModel by injection */
    val model: UserViewModel by viewModels()

    /**
     * Setup the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        loginButton.setOnClickListener { submit() }
        reset.setOnClickListener { forgot() }
        register.setOnClickListener { register() }
        observeLoginFlow()
    }

    /**
     * Switch the the registration activity
     */
    private fun register() {
        startActivity(intentFor<RegisterActivity>())
    }

    /**
     * Switch the the password reset activity
     */
    private fun forgot() {
        startActivity(intentFor<ResetActivity>())
    }

    /**
     * Submit the login request
     */
    private fun submit() {
        if (!validate()) return
        model.login(username.text.toString(), password.text.toString())
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
                displayProgressBar(it.loading)
                if (!it.message.isNullOrEmpty()) toast("${it.message}")
                if (it.data != null) {
                    startActivity(intentFor<MainActivity>().putExtra("user", it.data))
                }
            }
        )
    }

    /**
     * Validate the EditText fields
     * to make sure all inputs are
     * valid
     */
    private fun validate(): Boolean {
        val required = getString(R.string.required)
        return username.validate(required) { it.isNotEmpty() } &&
            password.validate(required) { it.isNotEmpty() }
    }

    /**
     * Display the progressbar while the data is still
     * loading
     */
    private fun displayProgressBar(visibility: Boolean) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}
