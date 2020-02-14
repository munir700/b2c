package co.yap.networking.cards.requestdtos

import com.google.gson.annotations.SerializedName

data class ReorderCardRequest(
    @SerializedName("cardSerialNumber") val cardSerialNumber: String? = null,
    @SerializedName("address1") val address1: String? = null,
    @SerializedName("latitude") val latitude: String? = null,
    @SerializedName("longitude") val longitude: String? = null
)