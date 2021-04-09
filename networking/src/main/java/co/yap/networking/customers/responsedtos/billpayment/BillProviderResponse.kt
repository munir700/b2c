package co.yap.networking.customers.responsedtos.billpayment

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

class BillProviderResponse(
    @SerializedName("data")
    val billProviders: List<BillProviderModel>
) : ApiResponse()
