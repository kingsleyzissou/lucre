package com.aquatic.lucre.models

import android.os.Parcelable
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import kotlinx.android.parcel.Parcelize

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
    override var id: String? = NanoIdUtils.randomNanoId(),
    override var userId: String? = null
) : Model, Parcelable {

    /**
     * Override to display category name
     * for combobox selection
     */
    override fun toString(): String {
        return name!!
    }
}
