package co.yap.modules.onboarding.enums

import androidx.annotation.Keep

@Keep
sealed class EidInfoEvents(val eventId: Int) {
    object EVENT_RESCAN : EidInfoEvents(1)
    object EVENT_ERROE_UNDERAGE : EidInfoEvents(2)
    object EVENT_ERROR_EXPIRED_EID : EidInfoEvents(3)
    object EVENT_ERROR_FROM_USA : EidInfoEvents(4)
    object EVENT_NEXT_WITH_ERROR : EidInfoEvents(5)
    object EVENT_NEXT : EidInfoEvents(6)
    object EVENT_FINISH : EidInfoEvents(7)
    object EVENT_ERROR_INVALID_EID : EidInfoEvents(8)
    object EVENT_ALREADY_USED_EID : EidInfoEvents(1041)
    object EVENT_EID_UPDATE : EidInfoEvents(9)
    object EVENT_CITIZEN_NUMBER_ISSUE : EidInfoEvents(10)
    object EVENT_EID_EXPIRY_DATE_ISSUE : EidInfoEvents(11)
    object EVENT_EID_ABOUT_TO_EXPIRY_DATE_ISSUE : EidInfoEvents(12)

}