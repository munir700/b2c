package co.yap.networking.cards.responsedtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    @SerializedName("creationDate")
    var creationDate: String? = null,
    @SerializedName("createdBy")
    var createdBy: String? = null,
    @SerializedName("updatedDate")
    var updatedDate: String? = null,
    //MEETING
    @SerializedName("addressType")
    var addressType: String? = null,
    @SerializedName("cardSerialNumber")
    var cardSerialNumber: String? = null,
    @SerializedName("accountUuid")
    var accountUuid: String? = null,
    @SerializedName("address1")
    var address1: String? = null,
    @SerializedName("address2")
    var address2: String? = null,
    @SerializedName("latitude")
    var latitude: Double? = null,
    @SerializedName("longitude")
    var longitude: Double? = null,
    @SerializedName("city")
    var city: String? = null,
    @SerializedName("country")
    var country: String? = null,
    @SerializedName("postalCode")
    var postalCode: String? = null,
    @SerializedName("uuid")
    var uuid: String? = null,
    @SerializedName("active")
    var active: Boolean? = null
) : Parcelable