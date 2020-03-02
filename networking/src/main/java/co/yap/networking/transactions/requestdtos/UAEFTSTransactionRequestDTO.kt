package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

data class UAEFTSTransactionRequestDTO(
    @SerializedName("beneficiaryId") var beneficiaryId: String?,
    @SerializedName("amount") var amount: Double?,
    @SerializedName("settlementAmount") var settlementAmount: Double?,
    @SerializedName("purposeCode") var purposeCode: String?,
    @SerializedName("purposeReason") var purposeReason: String?,
    @SerializedName("remarks") var remarks: String?
)