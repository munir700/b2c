package co.yap.modules.dashboard.models

import java.util.*
import kotlin.collections.ArrayList

data class TransactionAdapterModel(
    var viewType: String,
    var date: String,
    var totalAmount:String,
    var closingBalance:String,
    var vendor: String,
    var type: String,
    var imageUrl:String,
    var time: String,
    var category:String,
    var amount: String,
    var currency:String,
    var amountPercentage: String
)
