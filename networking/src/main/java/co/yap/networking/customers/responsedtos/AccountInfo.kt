package co.yap.networking.customers.responsedtos

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.sendmoney.CountryModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
class AccountInfo(
    @SerializedName("creationDate")
    var creationDate: String? = null,
    @SerializedName("createdBy")
    var createdBy: String?="",
    @SerializedName("updatedDate")
    var updatedDate: String?="",
    @SerializedName("uuid")
    var uuid: String?="",
    @SerializedName("defaultProfile")
    var defaultProfile: Boolean?=false,
    @SerializedName("accountType")
    var accountType: String? = "",
    @SerializedName("isActive")
    var isActive: String,
    @SerializedName("accountNo")
    var accountNo: String? = null,
    @SerializedName("fssRequestRefNo")
    var fssRequestRefNo: String,
    @SerializedName("packageName")
    var packageName: String?,
    @SerializedName("status")
    var status: String,
    @SerializedName("onBoardingStatus")
    var onBoardingStatus: String,
    @SerializedName("customer")
     var currentCustomer: Customer,
    @SerializedName("documentInformation")
    var documentInformation: String?="",
    @SerializedName("bank")
    var bank: Bank? = Bank(),
    @SerializedName("currency")
    var currency: CountryModel.Data.Currency = CountryModel.Data.Currency(),
    @SerializedName("notificationStatuses")
    var notificationStatuses: String,
    @SerializedName("toClose")
    var toClose: Boolean?=false,
    @SerializedName("noOfSubAccounts")
    var noOfSubAccounts: Int?=0,
    @SerializedName("parentUUID")
    var parentUUID: String? = null,
    @SerializedName("parentAccount")
    var parentAccount: AccountInfo? = null,
    @SerializedName("workItemNo")
    var workItemNo: String?="",
    @SerializedName("partnerBankStatus")
    var partnerBankStatus: String? = null,
    @SerializedName("active")
    var active: Boolean?=false,
    @SerializedName("soleProprietary")
    var soleProprietary: Boolean,
    @SerializedName("iban")
    var iban: String? = null,
    @SerializedName("ibdocumentsVerifiedan")
    var ibdocumentsVerifiedan: Boolean?=false,
    @SerializedName("documentsVerified")
    var documentsVerified: Boolean?=false,
    @SerializedName("isDocumentsVerified")
    var isDocumentsVerified: String? = null

//    private var currentCustomerLiveData: MutableLiveData<Customer>
):Parcelable {

//    var currentCustomer: Customer
//        get() = currentCustomerLiveData.value!!
//        set(value) {
//            currentCustomerLiveData = MutableLiveData()
//            currentCustomerLiveData.value = (value)
//        }
//
//    init {
//        currentCustomerLiveData = MutableLiveData()
//        currentCustomer = customer
//    }
//
//    fun getCustomerLiveData(): MutableLiveData<Customer> {
//        return currentCustomerLiveData
//    }
//
//    fun setLiveData() {
//        currentCustomer = customer
//    }
}