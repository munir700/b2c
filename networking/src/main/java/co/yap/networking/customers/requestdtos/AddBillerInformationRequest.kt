package co.yap.networking.customers.requestdtos

import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName

data class AddBillerInformationRequest(
    @SerializedName("billerID")
    val billerID: String,
    @SerializedName("skuId")
    val skuId: String,
    @SerializedName("billNickName")
    val billNickName: String,
    @SerializedName("inputsData")
    val inputsData: List<BillerInputData>,
    @SerializedName("isDown")
    val isDown: Int?=null
)