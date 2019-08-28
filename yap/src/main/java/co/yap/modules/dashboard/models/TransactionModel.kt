package co.yap.modules.dashboard.models

import java.util.*

data class TransactionModel(
    var vendor: String,
    var amount: Double,
    var amountPercentage: Double,
    var type: String,
    var date: String,
    var time: String,
    var category:String,
    var currency:String
)
