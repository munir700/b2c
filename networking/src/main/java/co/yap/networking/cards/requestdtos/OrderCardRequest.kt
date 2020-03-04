package co.yap.networking.cards.requestdtos

import com.google.gson.annotations.SerializedName

data class OrderCardRequest(
    @SerializedName("nearestLandMark") val nearestLandMark: String? = null,
    @SerializedName("cardName") val cardName: String? = null,
    @SerializedName("address1") val address1: String? = null,
    @SerializedName("address2") val address2: String? = null,
    @SerializedName("latitude") val latitude: Double? = 0.0,
    @SerializedName("longitude") val longitude: Double? = 0.0,
    @SerializedName("city") val city: String? = null,
    @SerializedName("country") val country: String? = null,
    @SerializedName("designCode") val designCode: String? = null

)