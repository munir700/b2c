package co.yap.modules.dashboard.models

import java.util.*
import kotlin.collections.ArrayList

data class TransactionModel(
    var type: String,
    var date: String,
    var totalAmount:String,
    var closingBalance:String,
    val transaction:ArrayList<Transaction>
)
