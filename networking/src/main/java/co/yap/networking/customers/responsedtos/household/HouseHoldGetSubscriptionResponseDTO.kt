package co.yap.networking.customers.responsedtos.household

import androidx.databinding.ObservableField
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class HouseHoldGetSubscriptionResponseDTO(
    @SerializedName("id")
    val id: ObservableField<String>? = null,
    @SerializedName("planType")
    val planType: ObservableField<String>? = null,
    @SerializedName("startDate")
    val startDate: ObservableField<String>? = null,
    @SerializedName("endDate")
    val endDate: ObservableField<String>? = null,
    @SerializedName("price")
    val price: ObservableField<String>? = null,
    @SerializedName("isAutoRenew")
    val isAutoRenew: ObservableField<Boolean>? = null

) : ApiResponse() {

}