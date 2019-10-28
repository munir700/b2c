package co.yap.networking.customers.responsedtos

import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.*


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

    fun getFormattedPhone(): String {
        return try {
            val pnu = PhoneNumberUtil.getInstance()
            val pn = pnu.parse(mobileNo, Locale.getDefault().country)
            return pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }


    fun getPicture(): String {
        return if (profilePictureName.isNullOrEmpty()) "" else profilePictureName!!
    }

    fun setPicture(picture: String?) {
        profilePictureName = picture
    }

}
