package co.yap.yapcore.helpers

import android.text.Editable
import android.text.TextUtils
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

class PhoneNumberUtils {
    fun getNationalNumber(editable: Editable, code: Int, codeString: String): String? {


        val phoneUtil = PhoneNumberUtil.getInstance()
        val tempNo = editable.toString().trim()
        var number: String? = null
        try {
            val ph = phoneUtil.parse(tempNo, codeString)
            ph.setCountryCode(code)
            number = "+" + ph.getCountryCode() + "" + ph.getNationalNumber()
            return number
        } catch (e: NumberParseException) {
        }

        return number
    }

    fun getCodeFromPhone(pNumber: String): Int {
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            // phone must begin with '+'
            val numberProto = phoneUtil.parse(pNumber, "PK")
            val pp = phoneUtil.getNationalSignificantNumber(numberProto)
            return numberProto.getCountryCode()
        } catch (e: NumberParseException) {
            System.err.println("NumberParseException was thrown: " + e.toString())
        }

        return 0
    }

    fun getInterNationalNumber(editable: Editable, codeString: String): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val tempNo = editable.toString().trim()
        var number: String? = null
        try {
            val ph = phoneUtil.parse(tempNo, codeString)
            ph.setCountryCode(ph.getCountryCode())
            number = phoneUtil.format(ph, PhoneNumberUtil.PhoneNumberFormat.E164)
            //number = "+" + ph.getCountryCode() + "" + ph.getNationalNumber();
            return number
        } catch (e: NumberParseException) {
        }
        return number
    }

   fun getInterNationalNumber(unFormattedNumber: String, codeString: String): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        var number: String? = null
        try {
            val ph = phoneUtil.parse(unFormattedNumber, codeString)
            ph.setCountryCode(ph.getCountryCode())
            number = phoneUtil.format(ph, PhoneNumberUtil.PhoneNumberFormat.E164)
            //number = "+" + ph.getCountryCode() + "" + ph.getNationalNumber();
            return number
        } catch (e: NumberParseException) {
        }
        return number
    }

    fun getNationalNumber(text: String, code: Int, codeString: String): String? {
        val phoneUtil = PhoneNumberUtil.getInstance()
        val tempNo = text.trim { it <= ' ' }
        var number: String? = null
        try {
            val ph = phoneUtil.parse(tempNo, codeString)
            ph.setCountryCode(code)

            number = "+" + ph.getCountryCode() + "" + ph.getNationalNumber()
            return number
        } catch (e: NumberParseException) {


        }

        return number
    }

    fun getNationalNumber(number: String): String {
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val ph = phoneUtil.parse(number, "")
            ph.nationalNumber
            return "" + ph.nationalNumber
        } catch (e: NumberParseException) {
        }
        return " "
    }

    fun isValidPhoneNumber(no: Editable, code: String): Boolean {
        if (TextUtils.isEmpty(no)) {
            return false
        }
        val number = no.toString().trim()
        return isValidPhoneNumber(number, code)
    }

    fun isValidPhoneNumber(number: String, code: String): Boolean {
        if (TextUtils.isEmpty(number)) {
            return false
        }
        var isValid = false
        val phoneUtil = PhoneNumberUtil.getInstance()
        var isMobile: PhoneNumberUtil.PhoneNumberType? = null
        try {
            val ph = phoneUtil.parse(number, code)
            ph.setCountryCode(phoneUtil.getCountryCodeForRegion(code))
            isValid = phoneUtil.isValidNumber(ph) && !number.startsWith("0")
            isMobile = phoneUtil.getNumberType(ph)
            return isValid && (PhoneNumberUtil.PhoneNumberType.MOBILE === isMobile || PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE === isMobile)
        } catch (e: NumberParseException) {
            return isValid
        }

    }

    fun parseContact(contact: String, code: String): String? {
        var phoneNumber: Phonenumber.PhoneNumber? = null
        val phoneNumberUtil = PhoneNumberUtil.getInstance()
        var finalNumber: String? = null
        // String isoCode = phoneNumberUtil.getRegionCodeForCountryCode(Integer.parseInt(countrycode));
        var isValid = false
        var isMobile: PhoneNumberUtil.PhoneNumberType? = null
        try {
            phoneNumber = phoneNumberUtil.parse(contact, code)
            isValid = phoneNumberUtil.isValidNumber(phoneNumber)
            isMobile = phoneNumberUtil.getNumberType(phoneNumber)

        } catch (e: NumberParseException) {
            e.printStackTrace()
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }


        if (isValid && (PhoneNumberUtil.PhoneNumberType.MOBILE === isMobile || PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE === isMobile)) {
            finalNumber = phoneNumberUtil.format(
                phoneNumber,
                PhoneNumberUtil.PhoneNumberFormat.E164
            ).substring(1)
        }
        return finalNumber
    }

    fun getCountryCodeForRegion(code: String): Int {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return phoneUtil.getCountryCodeForRegion(code.toUpperCase())

    }

    fun getCountryCodeForRegion(code: Int): String {
        val phoneUtil = PhoneNumberUtil.getInstance()
        return phoneUtil.getRegionCodeForCountryCode(code)

    }
}