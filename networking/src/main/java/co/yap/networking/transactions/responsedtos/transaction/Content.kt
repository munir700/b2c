package co.yap.networking.transactions.responsedtos.transaction

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("accountUuid1")
    var accountUuid1: String,
    @SerializedName("amount")
    var amount: Double,
    @SerializedName("balanceAfter")
    var balanceAfter: Double,
    @SerializedName("balanceBefore")
    var balanceBefore: Double,
    @SerializedName("card1")
    var card1: String,
    @SerializedName("category")
    var category: String,
    @SerializedName("createdBy")
    var createdBy: String,
    @SerializedName("creationDate")
    var creationDate: String,
    @SerializedName("currency")
    var currency: String,
    @SerializedName("customerId1")
    var customerId1: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("iban1")
    var iban1: String,
    @SerializedName("initiator")
    var initiator: String,
    @SerializedName("paymentMode")
    var paymentMode: String,
    @SerializedName("processorRefNumber")
    var processorRefNumber: String,
    @SerializedName("productCode")
    var productCode: String,
    @SerializedName("productName")
    var productName: String,
    @SerializedName("remarks")
    var remarks: String,
    @SerializedName("senderName")
    var senderName: String,
    @SerializedName("receiverName")
    var receiverName: String?,
    @SerializedName("status")
    var status: String,
    @SerializedName("totalAmount")
    var totalAmount: Double,
    @SerializedName("transactionId")
    var transactionId: String,
    @SerializedName("txnCode")
    var txnCode: String,
    @SerializedName("txnType")
    var txnType: String,
    @SerializedName("updatedBy")
    var updatedBy: String,
    @SerializedName("updatedDate")
    var updatedDate: String,
    @SerializedName("userType1")
    var userType1: String,
    @SerializedName("title")
    var title: String?,
    @SerializedName("imgUrl")
    var txnImgUrl: String? = ""

)