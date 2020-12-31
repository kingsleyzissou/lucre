package com.aquatic.lucre.models

import android.os.Parcelable
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import kotlinx.android.parcel.Parcelize
import javax.json.Json
import javax.json.JsonObject

/**
 * Category model class for the various
 * income/expense categories. A category
 * comprises of a name, description and a
 * display color
 */
@Parcelize
data class Category(
    var name: String? = null,
    var description: String? = null,
    var color: String? = null,
    override var id: String = NanoIdUtils.randomNanoId()
) : Model, Parcelable {

    /**
     * JsonModel class for deserializing JSON fields
     * back to the Category model. Each field has a custom action to convert the
     * JSON object field back to the desired type. This method overrides
     * the JSONModel TornadoFX `updateModel` function
     */
    override fun updateModel(json: JsonObject) {
        name = json.getString("name")
        description = json.getString("description")
        color = json.getString("color")
        id = json.getString("id")
    }

    /**
     * Method for serializing category fields
     * down to JSON strings. This method overrides
     * the JSONModel TornadoFX `toJSON` function
     */
    override fun toJSON(): JsonObject {
        return Json.createObjectBuilder()
            .add("name", name)
            .add("description", description)
            .add("color", color)
            .add("id", id)
            .build()
    }

    /**
     * Override to display category name
     * for combobox selection
     */
    override fun toString(): String {
        return name!!
    }
}
