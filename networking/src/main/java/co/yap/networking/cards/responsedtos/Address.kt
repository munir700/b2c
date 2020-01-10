package co.yap.networking.cards.responsedtos

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var address1: String? = null,
    var address2: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null
): Parcelable