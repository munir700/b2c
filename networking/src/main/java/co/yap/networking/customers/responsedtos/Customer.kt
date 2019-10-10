package co.yap.networking.customers.responsedtos

class Customer(

    var status: String,
    var profilePictureName: String?,
    var email: String,
    var countryCode: String,
    var mobileNo: String,
    var customerId: String,
    var isMobileNoVerified: String,
    var isEmailVerified: String,
    var firstName: String,
    var lastName: String,
    var uuid: String,
    var password: String?,

    var emailVerified: Boolean,
    var mobileNoVerified: Boolean

)
