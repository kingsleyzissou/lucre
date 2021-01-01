package com.aquatic.lucre.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var username: String,
    var email: String,
    var id: String? = null
): Parcelable
