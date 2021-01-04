package com.aquatic.lucre.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User model class for handling the
 * application session user
 */
@Parcelize
data class User(
    var username: String? = null,
    var email: String? = null,
    var id: String? = null
) : Parcelable
