package com.aquatic.lucre.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.isValidEmail
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), AnkoLogger {

    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener { submit() }
    }

    private fun submit() {
        if (validate()) {
            progressBar.visibility = View.VISIBLE
            val user = User(
                username.text.toString(),
                email.text.toString()
            )
            auth?.createUserWithEmailAndPassword(user.email!!, password.text.toString())!!
                .addOnCompleteListener { signupComplete(it, user) }
        }
    }

    private fun signupComplete(task: Task<AuthResult>, user: User) {
        if (task.isSuccessful()) {
            user.id = auth?.getCurrentUser()?.uid!!
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(user.id!!)
                .set(user)
                .addOnCompleteListener {
                    if (it.isSuccessful()) {
                        toast("Registration successful")
                        progressBar.visibility = View.GONE
                        startActivity(intentFor<MainActivity>().putExtra("user", user))
                    } else {
                        toast("Something went wrong")
                        progressBar.visibility = View.GONE
                        error("Signup error: ${it.exception?.message }")
                    }
                }
            return
        }
        toast("Registration failed. Please try again")
        progressBar.visibility = View.GONE
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
}
