package com.aquatic.lucre.models

import android.os.Parcelable
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import kotlinx.android.parcel.Parcelize
import javax.json.JsonObject
import javax.json.JsonObjectBuilder

/**
 * Vault model class for the accounts or vaults in the app.
 * A vault comprises of a name, description and a
 * currency
 */
@Parcelize
data class Vault(
    var name: String? = null,
    var description: String? = null,
    var currency: String? = null,
    override var id: String = NanoIdUtils.randomNanoId()
) : Model(), Parcelable {

    /**
     * JsonModel class for deserializing JSON fields
     * back to the Category model. Each field has a custom action to convert the
     * JSON object field back to the desired type. This method overrides
     * the JSONModel TornadoFX `updateModel` function
     */
    override fun updateModel(json: JsonObject) {
        name = json.getString("name")
        description = json.getString("description")
        currency = json.getString("currency")
        id = json.getString("id")
    }

    /**
     * Method for serializing category fields
     * down to JSON strings. This method overrides
     * the JSONModel TornadoFX `toJSON` function
     */
    override fun toJSON(json: JsonObjectBuilder) {
        with(json) {
            add("name", name)
            add("description", description)
            add("currency", currency)
            add("id", id)
        }
    }
}
