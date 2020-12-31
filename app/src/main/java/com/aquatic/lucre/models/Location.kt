package com.aquatic.lucre.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Location(
    var title: String = "Marker",
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f
) : Parcelable
