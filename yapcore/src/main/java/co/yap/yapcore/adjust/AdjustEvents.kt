package co.yap.yapcore.adjust

import androidx.annotation.Keep

@Keep
enum class AdjustEvents(val type: String) {
    DELIVERY_CONFIRMED("66c7tk"),
    KYC_END("9um5u9"),
    KYC_START("mdcyli"),
    SET_PIN_END("cs2msk"),
    SET_PIN_START("smn577"),
    SIGN_UP_MOBILE_NUMBER_VERIFIED("6zou42"),
    SIGN_UP_END("4c9qmq"),
    SIGN_UP_START("73mcc8"),
    TOP_UP_END("jw0tz5"),
    TOP_UP_START("cadxmk"),
}