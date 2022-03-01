package com.yap.yappk.networking.microservices.customers.responsedtos.accountinfo

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Customer(

    @SerializedName("status")
    var status: String? = null,
    @SerializedName("profilePictureName")
    private var profilePictureName: String?,
    @SerializedName("email")
    var email: String? = "",
    @SerializedName("countryCode")
    var countryCode: String,
    @SerializedName("mobileNo")
    var mobileNo: String,
    @SerializedName("customerId")
    var customerId: String? = null,
    @SerializedName("isMobileNoVerified")
    var isMobileNoVerified: String? = null,
    @SerializedName("isEmailVerified")
    var isEmailVerified: String? = null,
    @SerializedName("firstName")
    var firstName: String? = "",
    @SerializedName("lastName")
    var lastName: String,
    @SerializedName("uuid")
    var uuid: String? = "",
    @SerializedName("password")
    var password: String?,
    @SerializedName("nationality")
    var nationality: String?,
    @SerializedName("nationalityId")
    var identityNo: String?,
    @SerializedName("emailVerified")
    var emailVerified: Boolean? = false,
    @SerializedName("mobileNoVerified")
    var mobileNoVerified: Boolean? = false,
    @SerializedName("homeCountry")
    var homeCountry: String? = null,
    @SerializedName("founder")
    var founder: Boolean? = false,
    @SerializedName("customerColor")
    var customerColor: String? = null

) : Parcelable
