package co.yap.yapcore.leanplum

import androidx.annotation.Keep

@Keep
enum class SignupEvents(val type: String) {
    SIGN_UP_START("Signup_phone start"),
    SIGN_UP_NUMBER("Signup_phone number"),
    SIGN_UP_NUMBER_ERROR("Signup_phone number error"),
    SIGN_UP_OTP_CORRECT("Signup_OTP correct"),
    SIGN_UP_PASSCODE_CREATED("Signup_passcode created"),
    SIGN_UP_NAME("Signup_name"),
    SIGN_UP_EMAIL("Signup_email"),
    SIGN_UP_END("Signup_end"),
    SIGN_UP_DATE("Signup_date"),
    SIGN_UP_TIME("Signup_time"),
    SIGN_UP_LENGHT("Signup_length"),
}

@Keep
enum class KYCEvents(val type: String) {
    SKIP_KYC("clicks on Skip to dashboard"),
    SIGN_UP_ENABLED_PERMISSION("Signup_enabled permissions"),
    KYC_US_CITIIZEN("KYC_deny_US citizen"),
    KYC_ORDERED("KYC_card ordered"),
    EID_FAILURE("EIDA callback - failure"),
    EID_UNDER_AGE_18("EIDA callback - under 18"),
    //KYC_US_CITIZEN("EIDA callback - US Citizen"),
    KYC_PROHIBITED_CITIIZEN("EIDA callback - CB prohibited citizens"),
    CARD_ACTIVE("PIN success")
}