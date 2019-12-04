package co.yap.networking.customers.responsedtos

import androidx.lifecycle.MutableLiveData

class AccountInfo(

    var creationDate: String? = null,
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
    private var customer: Customer,
    var documentInformation: Any,
    var bank: Bank,

    var notificationStatuses: String,
    var toClose: Boolean,
    var noOfSubAccounts: Int,
    var workItemNo: String,
    var partnerBankStatus: String,
    var active: Boolean,
    var soleProprietary: Boolean,
    var iban: String,
    var ibdocumentsVerifiedan: Boolean,
    var documentsVerified: Boolean,
    var isDocumentsVerified: String,
    private var currentCustomerLiveData: MutableLiveData<Customer>
) {

    var currentCustomer: Customer
        get() = currentCustomerLiveData.value!!
        set(value) {
            currentCustomerLiveData = MutableLiveData()
            currentCustomerLiveData.value = (value)
        }

    init {
        currentCustomerLiveData = MutableLiveData()
        currentCustomer = customer
    }

    fun getCustomerLiveData(): MutableLiveData<Customer> {
        return currentCustomerLiveData
    }

    fun setLiveData() {
        currentCustomer = customer
    }
}