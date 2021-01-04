package com.aquatic.lucre.models

import android.os.Parcelable
import com.aquatic.lucre.core.BaseModel
import kotlinx.android.parcel.Parcelize

/**
 * Location model class for use with the
 * Google Maps activity
 */
@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var title: String = "Marker",
    override var id: String? = "",
    override var userId: String? = "",
    override var deleted: Boolean? = false
) : BaseModel, Parcelable
