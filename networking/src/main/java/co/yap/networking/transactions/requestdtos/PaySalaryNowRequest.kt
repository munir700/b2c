package co.yap.networking.transactions.requestdtos
import com.google.gson.annotations.SerializedName


data class PaySalaryNowRequest(
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("beneficiaryName") var beneficiaryName: String? = "",
    @SerializedName("receiverUUID") var receiverUUID: String? = null,
    @SerializedName("remarks") var remarks: String? = "",
    @SerializedName("txnCategory") var txnCategory: String? = "Salary"
)