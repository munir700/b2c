package com.yap.yappakistan.networking.microservices.customers.requestsdtos


import com.google.gson.annotations.SerializedName

data class SendInviteFriendRequest(
    @SerializedName("inviterCustomerId")
    var inviterCustomerId: String,
    @SerializedName("referralDate")
    var referralDate: String
)