package co.yap.networking.transactions.requestdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TotalPurchaseRequest(
    @SerializedName("txnType")
    val txnType: String,
    @SerializedName("beneficiaryId")
    val beneficiaryId: String? = null,
    @SerializedName("receiverCustomerId")
    val receiverCustomerId: String? = null,
    @SerializedName("productCode")
    val productCode: String
) : Parcelable
