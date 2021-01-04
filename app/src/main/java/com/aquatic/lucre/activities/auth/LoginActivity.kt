package com.aquatic.lucre.activities.auth

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.aquatic.lucre.R
import com.aquatic.lucre.activities.MainActivity
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

    val model: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // TODO add signout functionality

        supportActionBar?.hide()

        loginButton.setOnClickListener { submit() }

        reset.setOnClickListener { forgot() }
        register.setOnClickListener { register() }
        observeLoginFlow()
    }

    private fun register() {
        startActivity(intentFor<RegisterActivity>())
    }

    private fun forgot() {
        startActivity(intentFor<ResetActivity>())
    }

    private fun submit() {
        if (!validate()) return
        model.login(username.text.toString(), password.text.toString())
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

    private fun validate(): Boolean {
        val required = getString(R.string.required)
        return username.validate(required) { it.isNotEmpty() } &&
            password.validate(required) { it.isNotEmpty() }
    }

    private fun displayProgressBar(visibility: Boolean) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}
