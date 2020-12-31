package com.aquatic.lucre.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.json.Json
import javax.json.JsonObject

@Parcelize
data class Location(
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var zoom: Float = 0f,
    var title: String = "Marker",
    override var id: String = ""
) : Model, Parcelable {

    override fun updateModel(json: JsonObject) {
        lat = json.getString("lat").toString().toDouble()
        lng = json.getString("lng").toString().toDouble()
        zoom = json.getString("zoom").toString().toFloat()
        title = json.getString("title")
    }

    override fun toJSON(): JsonObject {
        return Json.createObjectBuilder()
            .add("lat", lat.toString())
            .add("lng", lng.toString())
            .add("zoom", zoom.toString())
            .add("title", title)
            .build()
    }
}
