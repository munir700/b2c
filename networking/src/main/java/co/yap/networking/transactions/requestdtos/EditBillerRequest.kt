package co.yap.networking.transactions.requestdtos

import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName


data class EditBillerRequest(
    @SerializedName("id")
    val id: Int,
    @SerializedName("billerID")
    val billerID: String,
    @SerializedName("skuId")
    val skuId: String,
    @SerializedName("billNickName")
    val billNickName: String,
    @SerializedName("autoPayment")
    val autoPayment: Boolean,
    @SerializedName("reminderNotification")
    val reminderNotification: Boolean,
    @SerializedName("reminderFrequency")
    val reminderFrequency: Int?,
    @SerializedName("inputsData")
    val inputsData: List<BillerInputData>?
)