package com.aquatic.lucre.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.aquatic.lucre.R
import com.aquatic.lucre.extensions.validate
import com.aquatic.lucre.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.password
import kotlinx.android.synthetic.main.activity_login.progressBar
import kotlinx.android.synthetic.main.activity_login.username
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), AnkoLogger {

    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        loginButton.setOnClickListener { submit() }

        reset.setOnClickListener { forgot() }
        register.setOnClickListener { register() }
    }

    private fun register() {
        startActivity(intentFor<RegisterActivity>())
    }

    private fun forgot() {
        TODO("Not yet implemented")
    }

    private fun submit() {
        if (validate()) {
            progressBar.visibility = View.VISIBLE
            auth?.signInWithEmailAndPassword(username.text.toString(), password.text.toString())!!
                .addOnCompleteListener { loginComplete(it) }
        }
    }

    private fun loginComplete(task: Task<AuthResult>) {
        if (task.isSuccessful()) {
            val uid = auth?.getCurrentUser()?.uid!!
            FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    val user = document.toObject(User::class.java)
                    progressBar.visibility = View.GONE
                    startActivity(intentFor<MainActivity>()?.putExtra("user", user))
                }
                .addOnFailureListener {
                    progressBar.visibility = View.GONE
                    error("Login exception: ${it.message}")
                    toast("Login error. Please try again")
                }
            return
        }
        toast("Login error. Please try again")
        error("Login exception: ${task.exception?.message}")
    }

    private fun validate(): Boolean {
        val required = getString(R.string.required)
        return username.validate(required) { it.isNotEmpty() } &&
            password.validate(required) { it.isNotEmpty() }
    }
}
