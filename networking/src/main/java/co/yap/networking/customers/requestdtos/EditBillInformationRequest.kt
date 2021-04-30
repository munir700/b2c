package co.yap.networking.customers.requestdtos

import co.yap.networking.customers.models.BillerInputData
import com.google.gson.annotations.SerializedName

data class EditBillInformationRequest(
    @SerializedName("billerID")
    val billerID: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("skuId")
    val skuId: String? = null,
    @SerializedName("billNickName")
    val billNickName: String? = null,
    @SerializedName("inputsData")
    val inputsData: List<BillerInputData>? = null
)
