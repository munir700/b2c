package co.yap.networking.customers.responsedtos.billpayment

import com.google.gson.annotations.SerializedName

data class IoCatalogsModel(
    @SerializedName("Name")
    var name: String?,
    @SerializedName("Datatype")
    var dataType: String?,
    @SerializedName("Description")
    var description: String?,
    @SerializedName("ValidLengths")
    var validLengths: String?,
    @SerializedName("MinLength")
    var minLength: String?,
    @SerializedName("MaxLength")
    var maxLength: String?,
    @SerializedName("IOID")
    var ioid: String?
)
