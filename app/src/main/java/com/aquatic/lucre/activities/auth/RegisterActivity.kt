package com.aquatic.lucre.activities.auth

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.MainActivity
import com.aquatic.lucre.extensions.isValidEmail
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.models.User
import com.aquatic.lucre.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.password
import kotlinx.android.synthetic.main.activity_register.progressBar
import kotlinx.android.synthetic.main.activity_register.username
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), AnkoLogger {

    val model: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        registerButton.setOnClickListener { submit() }
        login.setOnClickListener { login() }

        observeLoginFlow()
    }

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

    private fun login() {
        startActivity(intentFor<LoginActivity>())
    }

    private fun submit() {
        if (!validate()) return
        val user = User(
            username.text.toString(),
            email.text.toString()
        )
        model.register(user, password.text.toString())
    }

    private fun validate(): Boolean {
        val required = getString(R.string.required)
        return username.validate(required) { it.isNotEmpty() } &&
            email.validate(required) { it.isNotEmpty() } &&
            email.validate(getString(R.string.email)) { it.isValidEmail() } &&
            password.validate(required) { it.isNotEmpty() } &&
            password.validate(getString(R.string.length)) { it.length >= 6 } &&
            confirm.validate(required) { it.isNotEmpty() } &&
            confirm.validate(getString(R.string.match)) { password.text.toString().equals(it) }
    }

    private fun displayProgressBar(visibility: Boolean) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}
