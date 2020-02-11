package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

data class CashPayoutRequestDTO(
    @SerializedName("amount") var amount: Double?,
    @SerializedName("currency") var currency: String?,
    @SerializedName("purposeCode") var purposeCode: String?,
    @SerializedName("beneficiaryId") var beneficiaryId: Int?,
    @SerializedName("remarks") var remarks: String?
)