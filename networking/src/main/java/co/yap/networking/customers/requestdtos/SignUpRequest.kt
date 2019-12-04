package co.yap.networking.customers.requestdtos

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SignUpRequest(
    @SerializedName("firstName")
    var firstName: String? = null,
    @SerializedName("lastName")
    var lastName: String? = null,
    @SerializedName("countryCode")
    var countryCode: String? = null,
    @SerializedName("mobileNo")
    var mobileNo: String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("passcode")
    var passcode: String? = null,
    @SerializedName("accountType")
    var accountType: String? = null
) : Serializable
