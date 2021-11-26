package co.yap.networking.customers.requestdtos

import com.google.gson.annotations.SerializedName

data class CardNameRequest(
    @SerializedName("customerIDNumber")
    val customerIDNumber : String? = null,
    @SerializedName("customerNationality")
    val customerNationality : String? = null,
    @SerializedName("customerIDFirstName")
    val customerIDFirstName : String? = null,
    @SerializedName("customerIDMiddleName")
    val customerIDMiddleName : String? = null,
    @SerializedName("customerIDLastName")
    val customerIDLastName : String? = null,
    @SerializedName("displayCardName")
    val displayCardName : String? = null,
    @SerializedName("cardSerialNumber")
    val cardSerialNumber : String? = null
)
