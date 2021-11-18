package co.yap.networking.customers.responsedtos.BirthInfoAmendment


import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName

data class BirthInfoAmendmentResponse(
    @SerializedName("channel")
    val channel: String? = null,
    @SerializedName("cityOfBirth")
    val cityOfBirth: String? = null,
    @SerializedName("countryCode")
    val countryCode: String? = null,
    @SerializedName("countryOfBirth")
    val countryOfBirth: String? = null,
    @SerializedName("customerColor")
    val customerColor: String? = null,
    @SerializedName("customerId")
    val customerId: String? = null,
    @SerializedName("dob")
    val dob: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("firstName")
    val firstName: String? = null,
    @SerializedName("founder")
    val founder: Boolean? = null,
    @SerializedName("gender")
    val gender: String? = null,
    @SerializedName("homeCountry")
    val homeCountry: String? = null,
    @SerializedName("isEmailVerified")
    val isEmailVerified: Boolean? = null,
    @SerializedName("isMobileNoVerified")
    val isMobileNoVerified: Boolean? = null,
    @SerializedName("lastName")
    val lastName: String? = null,
    @SerializedName("mobileNo")
    val mobileNo: String? = null,
    @SerializedName("nationality")
    val nationality: String? = null,
    @SerializedName("nationalityId")
    val nationalityId: String? = null,
    @SerializedName("profilePictureName")
    val profilePictureName: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("usNationalForTax")
    val usNationalForTax: Boolean? = null,
    @SerializedName("uuid")
    val uuid: String? = null
) : ApiResponse()