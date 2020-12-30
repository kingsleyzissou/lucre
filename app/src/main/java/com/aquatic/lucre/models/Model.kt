package com.aquatic.lucre.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.json.JsonObject
import javax.json.JsonObjectBuilder

/**
 * Abstract class that is used
 * as the base class for all the models.
 *
 * This is to facilitate a more generic CRUDStore,
 * by ensuring that each store has an id element.
 */
interface Model {
    var id: String
    fun updateModel(jsonObject: JsonObject)
    fun toJSON(json: JsonObjectBuilder)
}
