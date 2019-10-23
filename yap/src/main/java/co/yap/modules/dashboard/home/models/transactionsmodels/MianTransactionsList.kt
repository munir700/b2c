package co.yap.modules.dashboard.home.models.transactionsmodels

import androidx.annotation.Nullable
import co.yap.networking.transactions.responsedtos.transaction.Content

data class MianTransactionsList(
    var type: String,
    var totalAmountType: String,
    var date: String,
    var totalAmount: String,
    var closingBalance: String,
    var amountPercentage: Double,
    @Nullable var transactionItems: ArrayList<Content>
)