package co.yap.networking.cards.responsedtos

import com.google.gson.annotations.SerializedName

data class CardDesignColor(
    @SerializedName("colorCode")
    val colorCode: String? = null,
    @SerializedName("designCodeUUID")
    val designCodeUUID: String? = null
)
