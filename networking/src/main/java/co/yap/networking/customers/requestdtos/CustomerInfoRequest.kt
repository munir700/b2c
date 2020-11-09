package co.yap.networking.customers.requestdtos

import com.google.gson.annotations.SerializedName

data class CustomerInfoRequest(
    @SerializedName("uuid") val mobileNo: String
)