package com.aquatic.lucre.extensions

import android.util.Patterns

// https://adrianhall.github.io/android/2018/04/11/easy-edittext-content-validation-with-kotlin/
fun String.isValidEmail(): Boolean
        = this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()