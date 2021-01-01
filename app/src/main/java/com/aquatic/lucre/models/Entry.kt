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
    var type: Enum<EntryType>? = null,
    var vendor: String? = null,
    var description: String? = null,
    var category: Category = Category(),
    var vault: String? = null,
    override var id: String? = NanoIdUtils.randomNanoId(),
    override var userId: String? = null,
    var date: LocalDate = LocalDate.now(),
    var image: String = "",
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
}
