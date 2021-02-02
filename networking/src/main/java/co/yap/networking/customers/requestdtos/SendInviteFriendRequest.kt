package co.yap.networking.customers.requestdtos

import com.google.gson.annotations.SerializedName

data class SendInviteFriendRequest(
    @SerializedName("inviteeCustomerId")
    val inviteeCustomerId: String,
    @SerializedName("referralDate")
    val referralDate: String
)