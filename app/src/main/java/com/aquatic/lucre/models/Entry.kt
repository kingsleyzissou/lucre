package com.aquatic.lucre.models

import android.os.Parcelable
import com.aqautic.lucre.repositories.CategoryStore
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import javax.json.JsonObject
import javax.json.JsonObjectBuilder

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
    var category: Category? = null,
    var vault: String? = null,
    override var id: String = NanoIdUtils.randomNanoId(),
    var date: LocalDate = LocalDate.now()
) : Model, Parcelable {

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
     * The entry object requires the entire category,
     * but for serialization purposes, only the category
     * id is saved. This method returns the correct category
     * for the given id
     */
    private fun categoryFromId(id: String): Category {
        val store = CategoryStore()
        return store.find(id)!!
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
        category = categoryFromId(json.getString("category"))
    }

    /**
     * Method for serializing category fields
     * down to JSON strings. This method overrides
     * the JSONModel TornadoFX `toJSON` function
     */
    override fun toJSON(json: JsonObjectBuilder) {
        with(json) {
            add("amount", amount.toString())
            add("type", type.toString())
            add("vendor", vendor)
            add("description", description)
            add("category", category!!.id)
            add("vault", vault)
            add("date", date.toString())
            add("id", id)
        }
    }
}
