package co.yap.networking.customers.requestdtos.billpayments

import com.google.gson.annotations.SerializedName

data class GetBillerCatalogRequest(
    @SerializedName("categoryId") val categoryId: String
)