package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

data class CoolingPeriodRequest(
    @SerializedName("beneficiaryId") val beneficiaryId: String,
    @SerializedName("beneficiaryCreationDate") val beneficiaryCreationDate: String,
    @SerializedName("beneficiaryName") val beneficiaryName: String,
    @SerializedName("amount") val amount: String
)