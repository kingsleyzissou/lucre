package com.aquatic.lucre.models

import android.os.Parcelable
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import kotlinx.android.parcel.Parcelize
import org.jetbrains.anko.AnkoLogger
import java.time.LocalDate
import javax.json.Json
import javax.json.JsonObject

/**
 * Entry model class for the apps income and expense
 * entries. An entry comprises of a vendor, amount,
 * expense type, description, category, vault
 * and date
 */
@Parcelize
data class Entry(
    var amount: Float? = null,
    var type: Enum<EntryType>? = null,
    var vendor: String? = null,
    var description: String? = null,
    var category: Category = Category(),
    var vault: String? = null,
    override var id: String = NanoIdUtils.randomNanoId(),
    var date: LocalDate = LocalDate.now(),
    var image: String = " ",
    var location: Location = Location()
) : Model, Parcelable, AnkoLogger {

    /**
     * Computed method to get the amount with
     * the correct sign. If the entry is an expense,
     * the value is negative. If it is income,
     * the amount will be positive.
     */
    val signedAmount: Float get() {
        if (type == EntryType.INCOME) {
            return amount!!
        }
        return amount!! * -1
    }

    /**
     * JsonModel class for deserializing JSON fields
     * back to the Category model. Each field has a custom action to convert the
     * JSON object field back to the desired type. This method overrides
     * the JSONModel TornadoFX `updateModel` function
     */
    override fun updateModel(json: JsonObject) {
        amount = json.getString("amount").toString().toFloat()
        type = EntryType.valueOf(json.getString("type"))
        vendor = json.getString("vendor")
        description = json.getString("description")
        vault = json.getString("vault")
        date = LocalDate.parse(json.getString("date"))!!
        id = json.getString("id")
        category.updateModel(json.getJsonObject("category"))
        location.updateModel(json.getJsonObject("location"))
    }

    /**
     * Method for serializing category fields
     * down to JSON strings. This method overrides
     * the JSONModel TornadoFX `toJSON` function
     */
    override fun toJSON(): JsonObject {
        return Json.createObjectBuilder()
            .add("amount", amount.toString())
            .add("type", type.toString())
            .add("vendor", vendor)
            .add("description", description)
            .add("category", category.toJSON())
            .add("vault", vault)
            .add("date", date.toString())
            .add("id", id)
            .add("location", location.toJSON())
            .build()
    }
}
