package co.yap.yapcore.enums

import androidx.annotation.Keep

@Keep
enum class NotificationStatus {
    ON_BOARDED,
    MEETING_SCHEDULED,
    MEETING_SUCCESS,
    MEETING_FAILED,
    CARD_ACTIVATED,
    PARNET_MOBILE_VERIFICATION_PENDING,
    PASS_CODE_PENDING,
    EID_RESCAN_REQ,
    EMAIL_PENDING
}