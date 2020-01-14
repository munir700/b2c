package co.yap.networking.customers.responsedtos

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class HouseHoldCardsDesignResponse(
    val data: List<HouseHoldCardsDesign?>? = null
) : ApiResponse() {
    data class HouseHoldCardsDesign(
        @SerializedName("uploadDate")
        val uploadDate: String? = null,
        @SerializedName("backSideDesignImage")
        val backSideDesignImage: String? = null,
        @SerializedName("designCodeUUID")
        val designCodeUUID: String? = null,
        @SerializedName("designCodeName")
        val designCodeName: String? = null,
        @SerializedName("designCode")
        val designCode: String? = null,
        @SerializedName("frontSideDesignImage")
        val frontSideDesignImage: String? = null,
        @SerializedName("status")
        val status: String? = null
    )
}
