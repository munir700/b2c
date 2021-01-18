package co.yap.modules.carddetaildialog

data class CardDetailsModel(
    var cardType: String? = "",
    var cardNumber: String? = "",
    var cardExpiry: String? = "",
    var cardCvv: String? = "",
    var displayName: String? = ""
) {
}