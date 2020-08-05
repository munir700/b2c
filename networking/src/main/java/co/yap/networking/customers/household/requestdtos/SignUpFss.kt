package co.yap.networking.customers.household.requestdtos

import com.google.gson.annotations.SerializedName


data class SignUpFss(
    @SerializedName("designCode") var designCode: String? = null,
    @SerializedName("productCode") var productCode: String? = null
)