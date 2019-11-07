package co.yap.networking.customers.responsedtos

import android.annotation.SuppressLint
import android.content.Context
import android.icu.util.TimeZone
import android.os.Build
import android.telephony.TelephonyManager
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

    fun getFormattedPhoneNumber(context: Context): String {
        return try {
            val pnu = PhoneNumberUtil.getInstance()
            val pn = pnu.parse(getCompletePhone(), getDefaultCountryCode(context))
            return pnu.format(pn, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    @SuppressLint("DefaultLocale")
    fun getCountryCodeFromTelephony(context: Context): String {
        val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return tm.networkCountryIso.toUpperCase()
    }

    fun getDefaultCountryCode(context: Context): String {
        val countryCode = getCountryCodeFromTimeZone(context)
        return if (countryCode == "") "AE" else countryCode
    }

    private fun getCountryCodeFromTimeZone(context: Context): String {
        val curTimeZoneId = Calendar.getInstance().timeZone.id
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            TimeZone.getRegion(curTimeZoneId)
        } else {
            getCountryCodeFromTelephony(context)
        }
    }

    fun getPicture(): String {
        return if (profilePictureName.isNullOrEmpty()) "" else profilePictureName!!
    }

    fun setPicture(picture: String?) {
        profilePictureName = picture
    }

}
