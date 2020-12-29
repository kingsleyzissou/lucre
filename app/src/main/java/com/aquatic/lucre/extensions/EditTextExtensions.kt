package com.aquatic.lucre.extensions

import android.widget.EditText

// https://adrianhall.github.io/android/2018/04/11/easy-edittext-content-validation-with-kotlin/
fun EditText.validate(message: String, validator: (String) -> Boolean): Boolean {
    if (!validator(this.text.toString())) {
        this.error = message
        return false
    }
    return true
}
