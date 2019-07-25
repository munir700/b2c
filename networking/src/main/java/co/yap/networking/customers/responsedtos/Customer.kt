package co.yap.networking.customers.responsedtos

class Customer(
    val uuid: String,
    val email: String,
    val countryCode: String,
    val mobileNo: String,
    val customerId: String,
    val isMobileNoVerified: String,
    val isEmailVerified: String,
    val firstName: String,
    val lastName: String
)
