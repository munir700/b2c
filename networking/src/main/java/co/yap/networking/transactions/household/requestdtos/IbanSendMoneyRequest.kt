package co.yap.networking.transactions.household.requestdtos

import android.os.Parcelable
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IbanSendMoneyRequest(
    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("beneficiaryName") var beneficiaryName: String? = null,
    @SerializedName("receiverUUID") var receiverUUID: String? = null,
    @SerializedName("remarks") var remarks: String? = null,
    @SerializedName("txnCategory") var txnCategory: String? = null,
    @SerializedName("txnSubCategory") var txnSubCategory: String? = null
) : ApiResponse(), Parcelable