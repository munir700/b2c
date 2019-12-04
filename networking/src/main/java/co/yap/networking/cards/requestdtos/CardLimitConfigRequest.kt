package co.yap.networking.cards.requestdtos

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CardLimitConfigRequest(
    @SerializedName("cardSerialNumber")
    val cardSerialNumber: String
) : Serializable
