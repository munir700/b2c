package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

data class SwiftTransactionRequestDTO(
    @SerializedName("beneficiaryId") var beneficiaryId: String?,
    @SerializedName("amount") var amount: Double?,
    @SerializedName("settlementAmount") var settlementAmount: Double?,
    @SerializedName("purposeCode") var purposeCode: String?,
    @SerializedName("purposeReason") var purposeReason: String?,
    @SerializedName("remarks") var remarks: String?,
    @SerializedName("fxRate") var fxRate: String?
) {
}