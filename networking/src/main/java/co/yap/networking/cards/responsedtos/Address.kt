package co.yap.networking.cards.responsedtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("address1")
    var address1: String? = null,
    @SerializedName("address2")
    var address2: String? = null,
    @SerializedName("latitude")
    var latitude: Double? = null,
    @SerializedName("longitude")
    var longitude: Double? = null,
    @Transient
    var city: String? = null,
    @Transient
    var country: String? = null
) : Parcelable