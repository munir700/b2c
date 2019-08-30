package co.yap.modules.dashboard.models

import androidx.annotation.Nullable


data class TransactionModel(
    var type: String,
    var totalAmountType: String,
    var date: String,
    var totalAmount: String,
    var closingBalance: String,
    var amountPercentage: Double,
    @Nullable var transactionItems: ArrayList<Transaction>
)