package com.aquatic.lucre.activities.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
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

    val model: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset)
        supportActionBar?.hide()

        cancel.setOnClickListener { goBack() }
        resetButton.setOnClickListener { submit() }

        observeLoginFlow()
    }

    private fun submit() {
        if (!validate()) return
        model.reset(email.text.toString())
    }

    private fun goBack() {
        startActivity(intentFor<LoginActivity>())
    }

    private fun observeLoginFlow() {
        model.datastate.observe(this, Observer {
            displayProgressBar(it.loading)
            if (!it.message.isNullOrEmpty()) toast("${it.message}")
            if (it.data != null) goBack()
        })
    }

    private fun validate(): Boolean {
        val required = getString(R.string.required)
        return email.validate(required) { it.isNotEmpty() } &&
                email.validate(getString(R.string.email)) { it.isValidEmail() }
    }

    private fun displayProgressBar(visibility: Boolean) {
        progressBar.visibility = if (visibility) View.VISIBLE else View.GONE
    }
}