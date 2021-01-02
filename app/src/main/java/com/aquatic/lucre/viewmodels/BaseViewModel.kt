package com.aquatic.lucre.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.jetbrains.anko.AnkoLogger

abstract class BaseViewModel<T> : ViewModel(), AnkoLogger {
    var list = MutableLiveData<List<T>>()
    val firestore = FirebaseFirestore.getInstance()
}