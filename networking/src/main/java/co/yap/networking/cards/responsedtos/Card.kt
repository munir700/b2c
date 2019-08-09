package co.yap.networking.cards.responsedtos


data class Card(
    val newPin: String,

    val cardType: String,

    val uuid: String,

    val physical: Boolean,

    val active: Boolean,

    val cardName: String,

    val status: String,

    val blocked: Boolean,

    val delivered: Boolean,

    val cardSerialNumber: String,

    val maskedCardNo: String,

    val atmAllowed: Boolean,

    val onlineBankingAllowed: Boolean,

    val retailPaymentAllowed: Boolean,

    val paymentAbroadAllowed: Boolean,

    val accountType: String,

    val expiryDate: String,

    val cardBalance: String,

    val cardScheme: String,

    val currentBalance: String,

    val availableBalance: String,

    val customerId: String,

    val accountNumber: String,

    val productCode: String,

    val pinCreated: Boolean
)