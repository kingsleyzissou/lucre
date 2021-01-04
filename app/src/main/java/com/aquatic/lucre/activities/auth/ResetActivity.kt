package com.aquatic.lucre.activities.auth

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.isValidEmail
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_reset.*
import kotlinx.android.synthetic.main.activity_reset.email
import kotlinx.android.synthetic.main.activity_reset.progressBar
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class ResetActivity : AppCompatActivity(), AnkoLogger {

    /* Get the User ViewModel by injection */
    val model: UserViewModel by viewModels()

    /**
     * Setup the activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        supportActionBar?.hide()
        cancel.setOnClickListener { goBack() }
        resetButton.setOnClickListener { submit() }
        observeLoginFlow()
    }

    /**
     * Submit the password reset request
     */
    private fun submit() {
        if (!validate()) return
        model.reset(email.text.toString())
    }

    /**
     * Go back to the login activity
     */
    private fun goBack() {
        startActivity(intentFor<LoginActivity>())
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
                if (it.data != null) goBack()
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
        return email.validate(required) { it.isNotEmpty() } &&
            email.validate(getString(R.string.email)) { it.isValidEmail() }
    }

    /**
     * Display the progressbar while the data is still
     * loading
     */
    private fun displayProgressBar(visibility: Boolean) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}
