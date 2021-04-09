package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class BillerCatalogResponse(
    @SerializedName("data")
    val billerCatalogs: List<BillerCatalogModel>
) : ApiResponse()
