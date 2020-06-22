package co.yap.yapcore.adjust

import androidx.annotation.Keep

@Keep
enum class AdjustEvents(val type: String) {
    //DELIVERY_CONFIRMED("66c7tk"),
    KYC_END("9um5u9"),
    KYC_START("mdcyli"),
    SET_PIN_END("cs2msk"),
    SET_PIN_START("smn577"),
    SIGN_UP_MOBILE_NUMBER_VERIFIED("6zou42"),
    SIGN_UP_END("4c9qmq"),
    SIGN_UP_START("73mcc8"),
    TOP_UP_END("jw0tz5"),
    TOP_UP_START("cadxmk"),
    INVITER("sgy2ni"),

    // House Hold Events
    HOUSE_HOLD_MAIN_USER_SUBSCRIPTION("temp"),
    HOUSE_HOLD_MAIN_SUB_PLAN_CONFIRM("temp"),
    HOUSE_HOLD_MAIN_PLAN_NAME("temp"),
    HOUSE_HOLD_MAIN_SHARE("temp"),
    ONBOARDING_START_NEW_HH_USER("temp"),//need to confirm the flow
    ONBOARDING_NEW_HH_USER_MAIN_USER_PHONE_CORRECT("temp"),
    ONBOARDING_NEW_HH_USER_EMAIL("temp"),
    ONBOARDING_NEW_HH_USER_SIGNUP("temp"),
    ONBOARDING_NEW_HH_USER_EID("temp"),
    ONBOARDING_NEW_HH_USER_EID_DECLINED("temp"),
    ONBOARD_NEW_HH_USER_ONBOARDING_SUCCESS("temp"),
    HH_USER_ACCOUNT_ACTIVE("temp"),
    HH_USER_EXISTING_ACCOUNT_ACTIVE("temp");
}
