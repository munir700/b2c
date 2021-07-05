package co.yap.billpayments.paybill.enum

sealed class ReminderType {
    data class ThreeDays(var rdays: Int = 3) : ReminderType()
    data class OneWeek(var rdays: Int = 7) : ReminderType()
    data class ThreeWeeks(var rdays: Int = 21) : ReminderType()
}