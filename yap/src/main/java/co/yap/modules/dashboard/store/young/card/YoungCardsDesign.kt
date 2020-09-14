package co.yap.modules.dashboard.store.young.card

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class YoungCardsDesign (
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
    val status: String? = null,
    @SerializedName("designColorCode")
    val designColorCode: String? = null,
    @SerializedName("productCode")
    val productCode: String? = null,
    @SerializedName("designCodeDescription")
    val designCodeDescription: String? = null,
    @SerializedName("editAndDelete")
    val editAndDelete: Boolean? = null
): ApiResponse()