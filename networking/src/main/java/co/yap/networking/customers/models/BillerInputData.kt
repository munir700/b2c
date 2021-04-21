package co.yap.networking.customers.models

import com.google.gson.annotations.SerializedName

data class BillerInputData(
    @SerializedName("key") val key: String,
    @SerializedName("value") val value: String
)