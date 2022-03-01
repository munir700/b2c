package com.yap.yappakistan.networking.microservices.customers.responsedtos

import com.google.gson.annotations.SerializedName

data class InviteeDetails(
    @SerializedName("inviteeCustomerId")
    var inviteeCustomerId: String? = null,
    @SerializedName("inviteeCustomerName")
    var inviteeCustomerName: String? = null,
    @Transient
    var title: String?,
    @Transient
    var subTitle: String?
)