package co.yap.networking.transactions.responsedtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TotalPurchasesResponse(
    @SerializedName("data")
    val data: TotalPurchases? = null
) : ApiResponse()

@Parcelize
data class TotalPurchases(
    @SerializedName("txnCount")
    val txnCount: String?,
    @SerializedName("avgSpendAmount")
    val avgSpendAmount: String?,
    @SerializedName("totalSpendAmount")
    val totalSpendAmount: String?
): Parcelable
