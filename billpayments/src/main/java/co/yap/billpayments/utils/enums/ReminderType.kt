package co.yap.billpayments.utils.enums

sealed class ReminderType {
    data class ThreeDays(var rdays: Int = 3) : ReminderType()
    data class OneWeek(var rdays: Int = 7) : ReminderType()
    data class TwoWeeks(var rdays: Int = 14) : ReminderType()
}