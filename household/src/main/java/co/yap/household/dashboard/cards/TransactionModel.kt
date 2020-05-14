package co.yap.household.dashboard.cards

import android.graphics.drawable.Drawable
import co.yap.networking.models.ApiResponse

data class TransactionModel (
    var name:String?="",
    var url:String?="",
    var time:String?="",
    var type:String?="",
    var amount:String?="",
    var percent:String?=""): ApiResponse()