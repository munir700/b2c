package co.yap.networking.transactions.responsedtos


import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InternationalFundsTransferReasonList(
    @SerializedName("errors")
    val errors: String, // null
    @SerializedName("data")
    val data: List<ReasonList>
) : Parcelable, ApiResponse() {

    @Parcelize
    data class ReasonList(
        @SerializedName("reason")
        val reason: String, // REAL ESTATE RELATED PAYMENTS
        @SerializedName("code")
        val code: String // 26
    ) : Parcelable
}