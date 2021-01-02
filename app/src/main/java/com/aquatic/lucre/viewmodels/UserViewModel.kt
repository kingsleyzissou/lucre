package com.aquatic.lucre.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aquatic.lucre.models.User
import com.aquatic.lucre.utilities.DataState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.jetbrains.anko.AnkoLogger

class UserViewModel: ViewModel(), AnkoLogger {

    val store = FirebaseFirestore.getInstance().collection("users")
    val auth = FirebaseAuth.getInstance()
    val datastate = MutableLiveData<DataState<User>>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            datastate.postValue(DataState.loading())
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                getUser(auth.currentUser?.uid!!)
            } catch (e: Exception) {
                datastate.postValue(DataState.error(e, e.message))
            }
        }
    }

    fun register(user: User, password: String) {
        viewModelScope.launch {
            datastate.postValue(DataState.loading(true))
            try {
                auth.createUserWithEmailAndPassword(user.email!!, password).await()
                store.document(user.id!!).set(user).await()
                datastate.postValue(DataState.data(user, "Registration complete"))
            } catch(e: Exception) {
                datastate.postValue(DataState.error(e, e.message))
            }
        }
    }

    fun reset(email: String) {
         viewModelScope.launch {
             datastate.postValue(DataState.loading(true))
             try {
                auth.sendPasswordResetEmail(email)
                datastate.postValue(DataState.data(User(), "Password email sent"))
             } catch(e: Exception) {
                datastate.postValue(DataState.error(e, e.message))
             }
         }
    }

    fun getUser(id: String) {
        viewModelScope.launch {
            try {
                val result = store.document(id).get().await()
                var user = result.toObject(User::class.java)
                datastate.postValue(DataState(false,null, user, "Sign in complete"))
            } catch(e: Exception) {
                datastate.postValue(DataState(false, e, null, e.message))
            }
        }
    }
}