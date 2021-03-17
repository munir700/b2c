package co.yap.networking.transactions.requestdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TotalPurchaseRequest(
    @SerializedName("txnType")
    val txnType: String? = null,
    @SerializedName("beneficiaryId")
    val beneficiaryId: Int? = null,
    @SerializedName("receiverCustomerId")
    val receiverCustomerId: Int? = null,
    @SerializedName("productCode")
    val productCode: String? = null
) : Parcelable
