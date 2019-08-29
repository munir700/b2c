package co.yap.modules.dashboard.models

import androidx.annotation.Nullable
import java.util.*
import kotlin.collections.ArrayList

data class TransactionModel(
    var type: String,
    var date: String,
    var totalAmount:String,
    var closingBalance:String,
    var amountPercentage:String,
    @Nullable var transactionsList:ArrayList<Transaction>
)