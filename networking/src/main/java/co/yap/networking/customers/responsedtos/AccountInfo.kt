package co.yap.networking.customers.responsedtos

import androidx.lifecycle.MutableLiveData
import com.google.gson.annotations.SerializedName

class AccountInfo(
    @SerializedName("creationDate")
    var creationDate: String? = null,
    @SerializedName("createdBy")
    var createdBy: String,
    @SerializedName("updatedDate")
    var updatedDate: String,
    @SerializedName("uuid")
    var uuid: String,
    @SerializedName("defaultProfile")
    var defaultProfile: Boolean,
    @SerializedName("isActive")
    var isActive: String,
    @SerializedName("accountNo")
    var accountNo: String?=null,
    @SerializedName("fssRequestRefNo")
    var fssRequestRefNo: String,
    @SerializedName("packageName")
    var packageName: String?,
    @SerializedName("status")
    var status: String,
    @SerializedName("onBoardingStatus")
    var onBoardingStatus: String,
    @SerializedName("customer")
    private var customer: Customer,
    @SerializedName("documentInformation")
    var documentInformation: Any,
    @SerializedName("bank")
    var bank: Bank?=Bank(),
    @SerializedName("notificationStatuses")
    var notificationStatuses: String,
    @SerializedName("toClose")
    var toClose: Boolean,
    @SerializedName("noOfSubAccounts")
    var noOfSubAccounts: Int,
    @SerializedName("workItemNo")
    var workItemNo: String,
    @SerializedName("partnerBankStatus")
    var partnerBankStatus: String?=null,
    @SerializedName("active")
    var active: Boolean,
    @SerializedName("soleProprietary")
    var soleProprietary: Boolean,
    @SerializedName("iban")
    var iban: String? = null,
    @SerializedName("ibdocumentsVerifiedan")
    var ibdocumentsVerifiedan: Boolean,
    @SerializedName("documentsVerified")
    var documentsVerified: Boolean,
    @SerializedName("isDocumentsVerified")
    var isDocumentsVerified: String? = null,
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