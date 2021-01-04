package com.aquatic.lucre.extensions

import android.widget.EditText

/**
 * This function is used for text fields to validate them before
 * submitting a form.
 *
 * The reference implementation for this can be found here:
 * // https://adrianhall.github.io/android/2018/04/11/easy-edittext-content-validation-with-kotlin/
 */
fun EditText.validate(message: String, validator: (String) -> Boolean): Boolean {
    if (!validator(this.text.toString())) {
        this.error = message
        return false
    }
    return true
}
