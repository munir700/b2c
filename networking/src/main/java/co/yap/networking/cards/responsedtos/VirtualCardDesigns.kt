package co.yap.networking.cards.responsedtos

import com.google.gson.annotations.SerializedName

data class VirtualCardDesigns(
    @SerializedName("designCodeUUID")
    val designCodeUUID: String? = null,
    @SerializedName("designCodeName")
    val designCodeName: String? = null,
    @SerializedName("designCode")
    val designCode: String? = null,
    @SerializedName("frontSideDesignImage")
    val frontSideDesignImage: String? = null,
    @SerializedName("backSideDesignImage")
    val backSideDesignImage: String? = null,
    @SerializedName("uploadDate")
    val uploadDate: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("designCodeColors")
    val designCodeColors: List<CardDesignColor>
)