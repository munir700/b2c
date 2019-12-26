package co.yap.networking.transactions.responsedtos

import com.google.gson.annotations.SerializedName

data class TxnAnalytic(
    @SerializedName("logoUrl")
    val logoUrl: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("totalSpending")
    val totalSpending: String? = null,
    @SerializedName("totalSpendingInPercentage")
    val totalSpendingInPercentage: Double? = null,
    @SerializedName("txnCount")
    val txnCount: Int? = null
)