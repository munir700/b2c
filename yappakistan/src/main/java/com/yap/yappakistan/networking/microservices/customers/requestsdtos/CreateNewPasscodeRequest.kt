package com.yap.yappakistan.networking.microservices.customers.requestsdtos

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateNewPasscodeRequest(
    @SerializedName("newPassword")
    var newPassword: String? = null,
    @SerializedName("token")
    var token: String? = null,
    @SerializedName("mobileNo")
    var mobileNo: String? = null
) : Parcelable
