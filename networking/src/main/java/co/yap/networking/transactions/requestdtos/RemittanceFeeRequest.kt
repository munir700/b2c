package co.yap.networking.transactions.requestdtos

import com.google.gson.annotations.SerializedName

data class RemittanceFeeRequest(
    @SerializedName("country") val country: String?,
    @SerializedName("amount") val amount: String
)