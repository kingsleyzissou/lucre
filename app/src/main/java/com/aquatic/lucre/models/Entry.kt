package com.aquatic.lucre.models

import android.os.Parcelable
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import kotlinx.android.parcel.Parcelize
import org.jetbrains.anko.AnkoLogger
import java.time.LocalDate

/**
 * Entry model class for the apps income and expense
 * entries. An entry comprises of a vendor, amount,
 * expense type, description, category, vault
 * and date
 */
@Parcelize
data class Entry(
    var amount: Float? = null,
    var type: String? = null,
    var vendor: String? = null,
    var description: String? = null,
    var category: String? = null,
    var vault: String? = null,
    override var id: String? = NanoIdUtils.randomNanoId(),
    override var userId: String? = null,
    var date: String = LocalDate.now().toString(),
    var image: String = "",
    var location: Location = Location(),
    override var deleted: Boolean? = false
) : Model, Parcelable, AnkoLogger {

    /**
     * Computed method to get the amount with
     * the correct sign. If the entry is an expense,
     * the value is negative. If it is income,
     * the amount will be positive.
     */
    val signedAmount: Float get() {
        if (type == EntryType.INCOME.toString()) {
            return amount!!
        }
        return amount!! * -1
    }
}
