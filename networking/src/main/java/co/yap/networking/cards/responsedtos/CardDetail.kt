package co.yap.networking.cards.responsedtos

class CardDetail(
    var productCode: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var displayName: String? = null,
    var customerId: String? = null,
    var mobileNumber: String? = null,
    var emailIdvar: String? = null,
    var proxyNumber: String? = null,
    var cvv: String? = null,
    var cardNumber: String? = null,
    var expiryDate: String? = null,
    var activationDate: String? = null,
    var cardStatus: Int? = 0,
    var nonSoleProprietary: Boolean = false
    )