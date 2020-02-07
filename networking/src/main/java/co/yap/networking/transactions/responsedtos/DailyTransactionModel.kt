package co.yap.networking.transactions.responsedtos

import com.google.gson.annotations.SerializedName

data class DailyTransactionModel(
    @SerializedName("totalDebitAmount")
    var totalDebitAmount: Double?,
    @SerializedName("dailyLimitConsumer")
    var dailyLimitConsumer: Double?
)