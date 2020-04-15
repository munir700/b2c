package co.yap.networking.customers.responsedtos.household

import com.google.gson.annotations.SerializedName

data class HouseHoldGetSubscription(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("planType")
    val planType: String? = null,
    @SerializedName("startDate")
    val startDate: String? = null,
    @SerializedName("endDate")
    val endDate: String? = null,
    @SerializedName("price")
    val price: String? = null,
    @SerializedName("isAutoRenew")
    var isAutoRenew: Boolean? = false,
    @SerializedName("isActive")
    var isActive: Boolean? = false

) {
}