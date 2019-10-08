package co.yap.networking.customers.responsedtos

class AccountInfo(

    var creationDate: String,
    var createdBy: String,
    var updatedDate: String,
    var uuid: String,
    var defaultProfile: Boolean,
    var isActive: String,
    var accountNo: String,
    var fssRequestRefNo: String,
    var packageName: String?,
    var status: String,
    var onBoardingStatus: String,
    var customer: Customer,
    var bank: Bank,

    var notificationStatuses: String,
    var toClose: Boolean,
    var noOfSubAccounts: Int,
    var workItemNo: String,
    var partnerBankStatus: String,
    var active: Boolean,
    var soleProprietary: Boolean,
    var iban: String,
    var ibdocumentsVerifiedan: Boolean

)