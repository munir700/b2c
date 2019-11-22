package co.yap.modules.dashboard.cards.analytics.models

data class AnalyticsItem(
    var title: String,
    var txnCount: Int,
    var totalSpending: Double,
    var totalSpendingInPercentage: Double,
    var logoUrl: String = "",

    var transactionType: String = "",
    var transactionCount: String = "",
    var SpentAmount: String = "",
    var spentPercentage: String = ""
)