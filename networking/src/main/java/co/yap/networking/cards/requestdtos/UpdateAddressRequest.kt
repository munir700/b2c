package co.yap.networking.cards.requestdtos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateAddressRequest(
    @SerializedName("address1")
    val address1: String? = null,
    @SerializedName("address2")
    val address2: String? = null,
    @SerializedName("latitude")
    val latitude: String? = null,
    @SerializedName("longitude")
    val longitude: String? = null
) : Serializable