package co.yap.yapcore.leanplum

object TrackEvents {

    //Signup
    const val SIGN_UP_START = "Signup_phone start"
    const val MOBILE_NUMBER_ENTERED = "Signup_phone number"
    const val MOBILE_NUMBER_ERROR = "Signup_phone number error"
    const val SIGN_UP_OTP_CORRECT = "Signup_OTP correct"
    const val SIGN_UP_PASSCODE_CREATED = "Signup_passcode created"
    const val FULL_NAME_ENTERED = "Signup_name"
    const val EMAIL_ADDRESS_ENTERED = "Signup_email"
    const val SIGN_UP_END = "Signup_end"

    //KYC
    const val CLICKS_ON_SKIP_TO_DASHBOARD = "clicks on Skip to dashboard"
    const val SIGN_UP_DATE = "Signup_date" // parmeter
    const val SIGN_UP_TIME = "Signup_time" //parameter
    const val SIGN_UP_PREMISSION = "Signup_enabled permissions" //parameter
    const val SIGN_UP_LENGHT = "Signup_length" //parameter

    const val EIDA_CALLBACK_FAILURE = "EIDA callback - failure"
    const val EIDA_CALLBACK_UNDER_18 = "EIDA callback - under 18"
    const val EIDA_CALLBACK_US_CITIZEN = "KYC_deny_US citizen"
    const val EIDA_CALLBACK_PROHIBITED_CITIZENS = "EIDA callback - CB prohibited citizens"
    const val CONFIRM_Address = "KYC_card ordered"
    const val TAWZEA_CALLBACK_ON_WAY = "Tawzea callback on its way"
    const val TAWZEA_CALLBACK_DELIVERED = "Tawzea callback delivered"
    const val RAKBANK_CALLBACK_SUCCESS = "confirm address"
    const val RAKBANK_CALLBACK_FAILURE = "confirm address"
    const val YAP_ONBOARDED = "yap on boarded"
}