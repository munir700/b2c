package co.yap.household.dashboard.cards

import co.yap.networking.models.ApiResponse

@Deprecated("used only for mocking the card transaction")
data class TransactionModel(
    var name: String? = "",
    var url: String? = "",
    var time: String? = "",
    var type: String? = "",
    var amount: String? = "",
    var percent: String? = ""
) : ApiResponse()