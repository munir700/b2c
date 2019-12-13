package co.yap.networking.transactions.responsedtos.transaction


import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class RemittanceFeeResponse(
    @SerializedName("errors")
    val errors: String? = null,
    @SerializedName("data")
    val data: RemittanceFee? = null
) : Parcelable, ApiResponse() {

    @Parcelize
    data class RemittanceFee(
        @SerializedName("feeType")
        val feeType: String? = null, // FLAT
        @SerializedName("displayOnly")
        val displayOnly: Boolean? = false, // false
        @SerializedName("tierRateDTOList")
        val tierRateDTOList: List<TierRateDTO>? = arrayListOf()
    ) : Parcelable {
        @Parcelize
        data class TierRateDTO(
            @SerializedName("feeAmount")
            val feeAmount: Double? = 0.0, // 10.0
            @SerializedName("vatAmount")
            val vatAmount: Double? = 0.0, // 0.5
            @SerializedName("uuid")
            val uuid: String? = null, // null
            @SerializedName("amountFrom")
            val amountFrom: String? = null, // null
            @SerializedName("amountTo")
            val amountTo: String? = null, // null
            @SerializedName("createdBy")
            val createdBy: String? = null, // null
            @SerializedName("createdOn")
            val createdOn: String? = null, // null
            @SerializedName("updatedBy")
            val updatedBy: String? = null, // null
            @SerializedName("updatedOn")
            val updatedOn: String? = null, // null
            @SerializedName("feeUuid")
            val feeUuid: String? = null // null
        ) : Parcelable
    }
}