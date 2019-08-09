package co.yap.networking.customers.responsedtos

class AccountInfo(
    var accountType: String,
    var iban: String,
    var notificationStatuses: String,
    var defaultProfile: Boolean,
    var customer: Customer
)