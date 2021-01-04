package com.aquatic.lucre.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.AnkoLogger

/**
 * Genereic BaseViewModel class. This class
 * contains all the shared methods of the
 * various ViewModels to avoid code duplication
 */
abstract class BaseViewModel<T> : ViewModel(), AnkoLogger {
    /* List of live date that can be observed */
    var list = MutableLiveData<List<T>>()
    /* Singleton firebase auth instance */
    val auth = FirebaseAuth.getInstance()
    /* Singleton firebase fire store instance */
    val firestore = FirebaseFirestore.getInstance()
}
