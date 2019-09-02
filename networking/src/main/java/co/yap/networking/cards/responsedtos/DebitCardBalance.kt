package co.yap.networking.cards.responsedtos

data class DebitCardBalance(
    var accountNumber: String? = null,
    var availableBalance: String? = null,
    var currencyCode: String? = null,
    var currencyDecimals: String? = null,
    var ledgerBalance: String? = null,
    var totalPendingAuth: String? = null
)