package com.aquatic.lucre.models

import android.os.Parcelable
import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import kotlinx.android.parcel.Parcelize

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
    override var id: String? = NanoIdUtils.randomNanoId(),
    override var userId: String? = null
) : Model, Parcelable
