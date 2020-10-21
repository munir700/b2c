package co.yap.networking.transactions.responsedtos.transaction

import co.yap.networking.transactions.responsedtos.AnalyticsTransaction
import com.google.gson.annotations.SerializedName

data class TransactionAnalyticsDetailsResponse(
    @SerializedName("totalSpending")
    val totalSpending: Double? = null,
    @SerializedName("monthlyToTotal")
    val monthlyToTotal: Double? = null,
    @SerializedName("averageSpending")
    val averageSpending: Double? = null,
    @SerializedName("currentToLastMonth")
    val currentToLastMonth: Double? = null,
    @SerializedName("transactionDetails")
    val txnAnalytics: ArrayList<AnalyticsTransaction>? = null
)