package co.yap.modules.dashboard.cards.analytics.models

data class AnalyticsItem(
    var transactionType: String,
    var transactionCount: String,
    var SpentAmount: String,
    var spentPercentage: String
)