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
    SIGN_UP_TIMESTAMP("Signup_timestamp"),
    SIGN_UP_LENGTH("Signup_length"),
}

@Keep
enum class KYCEvents(val type: String) {
    SKIP_KYC("clicks on Skip to dashboard"),
    SIGN_UP_ENABLED_PERMISSION("Signup_enabled permissions"),
    KYC_US_CITIIZEN("KYC_deny_US citizen"),
    KYC_ORDERED("KYC_card ordered"),
    EID_FAILURE("EIDA callback - failure"),
    EID_UNDER_AGE_18("EIDA callback - under 18"),// will be catered later
    KYC_PROHIBITED_CITIIZEN("EIDA callback - CB prohibited citizens"),
    CARD_ACTIVE("account_active"),
    KYC_ID_CONFIRMED("KYC_ID confirmed"),
    EID_EXPIRE("eid_expired"),
    EID_EXPIRE_DATE("eid_expiry_date")
}

@Keep
enum class CardEvents(val type: String) {
    VIRTUAL_CARD_SUCCESS("virtual_card_success"),
    CARD_CONTROL_INTERNATIONAL_ON("card_control_international_on"),
    CARD_CONTROL_INTERNATIONAL_OFF("card_control_international_off"),
    CARD_CONTROL_POS_ON("card_control_pos_on"),
    CARD_CONTROL_POS_OFF("card_control_pos_off"),
    CARD_CONTROL_ATM_ON("card_control_atm_on"),
    CARD_CONTROL_ATM_OFF("card_control_atm_off"),
    CARD_CONTROL_ONLINE_ON("card_control_online_on"),
    CARD_CONTROL_ONLINE_OFF("card_control_online_off"),
}

@Keep
enum class AnalyticsEvents(val type: String) {
    ANALYTICS_OPEN("analytics_open")
}

@Keep
enum class Y2YEvents(val type: String) {
    YAP_TO_YAP_SENT("yap_to_yap_sent"),
}

@Keep
enum class TopUpEvents(val type: String) {
    ACCOUNT_TOP_UP_CARD("account_top_up_card"),
    ACCOUNT_TOP_UP_TRANSFER("account_top_up_transfer"),
}

@Keep
enum class SendMoneyEvents(val type: String) {
    SEND_MONEY_LOCAL("send_money_local"),
    SEND_MONEY_INTERNATIONAL("send_money_international"), // params  (LastCountry, LastType )
    QR_PAYMENT_SUCCESS("qr_payment_success")
}

@Keep
enum class MoreB2CEvents(val type: String) {
    OPEN_ATM_MAP("open_atm_map")
}

@Keep
enum class SignInEvents(val type: String) {
    SIGN_IN("sign_in")
}

@Keep
enum class HHSubscriptionEvents(val type: String) {
    HH_START_SUBSCRIPTION("Household main start subscription"),
    HH_SUB_PLANS_CONFIRM("Household main subs plan confirm"),
    HH_PLAN_NAME("Household main plan name"),
    HH_PLAN_PHONE("Household main plan phone"),
    HH_PLAN_PHONE_ERROR("Household main plan phone error"),
    HH_PLAN_CONFIRM("Household main plan confirm"),
    HH_SHARE("Household main share")
}

@Keep
enum class HHUserActivityEvents(val type: String) {
    HH_HELP_USER_DECLINED("Household main Help user declined"),
    HH_SUBS_CANCEL("Household main subs cancel"),
    HH_ISSUING_ADDITIONAL_CARD("Household main issuing additional card"),
    HH_SALARY_PAID("Household main salary paid"),
    HH_RECURRING_SALARY_SET_AND_PAID("Household main recurring salary set and paid"),
    HH_RECURRING_SALARY_SET("Household main recurring salary set"),
    HH_EXPENSE_TRANSFERRED("Household main expense transferred")
}

@Keep
enum class HHUserOnboardingEvents(val type: String) {
    ONBOARDING_START_NEW_HH_USER("Onboarding start New HH user"),
    ONBOARDING_NEW_HH_USER_PHONE_CORRECT("Onboarding New HH user Main user phone correct"),
    ONBOARDING_NEW_HH_USER_PASSCODE_CREATED("Onboarding New HH user passcode created"),
    ONBOARDING_NEW_HH_USER_EMAIL("Onboarding New HH user email"),
    ONBOARDING_NEW_HH_USER_SIGN_UP("Onboarding New HH user sign up"),
    ONBOARDING_NEW_HH_USER_EID("Onboarding New HH user eID"),
    ONBOARDING_NEW_HH_USER_EID_DECLINED("Onboarding New HH user EID declined"),
    ONBOARDING_NEW_HH_USER_ONBOARDING_SUCCESS("Onboarding New HH user onboarding success"),
    HH_USER_KYC_CARD_DELIVERED("HH user KYC card delivered"),
    HH_USER_ACCOUNT_ACTIVE("HH user account active"),
    HH_USER_EXISTING_ACCOUNT_ACTIVE("HH user existing account active")
}

@Keep
enum class HHTransactionsEvents(val type: String) {
    HH_USER_CARD_BLOCKED_AND_REORDERED("HH user card blocked and reordered"),
    HH_USER_MONEY_REQUEST("HH user money request"),
    HH_USER_ADD_BENEFICIARY_SUCCESSFULLY_CASH("HH user add beneficiary successfully cash"),
    HH_USER_CASH_TRANSFER("HH user cash transfer"),
    HH_USER_ADD_BENEFICIARY_SUCCESSFULLY_BANK("HH user add beneficiary successfully bank"),
    HH_USER_BANK_TRANSFER("HH user bank transfer"),
    HH_USER_ADD_BENEFICIARY_SUCCESSFULLY_DOMESTIC("HH user add beneficiary successfully domestic"),
    HH_USER_BANK_TRANSFER_DOMESTIC("HH user bank transfer domestic"),
    HH_USER_ADD_BENEFICIARY_SUCCESSFULLY_INTL("HH user add beneficiary successfully intl"),
    HH_USER_BANK_TRANSFER_INTL("HH user bank transfer intl"),
}