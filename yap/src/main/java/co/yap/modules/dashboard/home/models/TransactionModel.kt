package co.yap.modules.dashboard.home.models

import androidx.annotation.Nullable
import co.yap.networking.transactions.responsedtos.transaction.Content

data class TransactionModel(
    var type: String,
    var totalAmountType: String,
    var date: String,
    var totalAmount: String,
    var closingBalance: Double,
    var amountPercentage: Double,
    @Nullable var transactionItems: ArrayList<Content>
)