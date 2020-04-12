package co.yap.networking.customers.responsedtos.household

import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class HouseHoldGetSubscriptionResponseDTO(
    @SerializedName("data")
    var data: HouseHoldGetSubscription
) : ApiResponse() {

}