package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

data class RMTTransactionRequestDTO(
    @SerializedName("amount") var amount: Double?,
    @SerializedName("currency") var currency: String?,
    @SerializedName("purposeCode") var purposeCode: String?,
    @SerializedName("beneficiaryId") var beneficiaryId: String?,
    @SerializedName("remarks") var remarks: String?,
    @SerializedName("purposeReason") var purposeReason: String?

)