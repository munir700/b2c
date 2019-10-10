package co.yap.networking.customers.responsedtos

class Customer(

    var status: String,
    private var profilePictureName: String?,
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

) {

    fun getFullName(): String {
        return "$firstName $lastName"
    }

    fun getCompletePhone(): String {
        return "$countryCode $mobileNo"
    }

    fun getPicture(): String {
        return if (profilePictureName.isNullOrEmpty()) "" else profilePictureName!!
    }

    fun setPicture(picture: String?) {
        profilePictureName = picture
    }

}
