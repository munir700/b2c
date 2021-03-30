package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BillProviderModel(
    @SerializedName("categoryId")
    var categoryId: String,
    @SerializedName("categoryName")
    var categoryName: String,
    @SerializedName("categoryType")
    var categoryType: String,
    @SerializedName("icon")
    var icon: String
) : ApiResponse()
