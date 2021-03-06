package co.yap.networking.customers.responsedtos

import android.os.Parcelable
import co.yap.networking.customers.responsedtos.sendmoney.Currency
import co.yap.networking.models.ApiResponse
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class AccountInfo(
    @SerializedName("creationDate")
    var creationDate: String? = null,
    @SerializedName("createdBy")
    var createdBy: String? = "",
    @SerializedName("updatedDate")
    var updatedDate: String? = "",
    @SerializedName("uuid")
    var uuid: String? = "",
    @SerializedName("defaultProfile")
    var defaultProfile: Boolean? = false,
    @SerializedName("accountType")
    var accountType: String? = "",
    @SerializedName("isActive")
    var isActive: String,
    @SerializedName("accountNo")
    var accountNo: String? = null,
    @SerializedName("fssRequestRefNo")
    var fssRequestRefNo: String?=null,
    @SerializedName("packageName")
    var packageName: String?,
    @SerializedName("status")
    var status: String?=null,
    @SerializedName("onBoardingStatus")
    var onBoardingStatus: String?=null,
    @SerializedName("customer")
    var currentCustomer: Customer,
    @SerializedName("bank")
    var bank: Bank? = Bank(),
    @SerializedName("currency")
    var currency: Currency = Currency(),
    @SerializedName("notificationStatuses")
    var notificationStatuses: String,
    @SerializedName("toClose")
    var toClose: Boolean? = false,
    @SerializedName("noOfSubAccounts")
    var noOfSubAccounts: Int? = 0,
    @SerializedName("parentUUID")
    var parentUUID: String? = null,
    @SerializedName("parentAccount")
    var parentAccount: AccountInfo? = null,
    @SerializedName("workItemNo")
    var workItemNo: String? = "",
    @SerializedName("partnerBankStatus")
    var partnerBankStatus: String? = null,
    @SerializedName("active")
    var active: Boolean? = false,
    @SerializedName("soleProprietary")
    var soleProprietary: Boolean,
    @SerializedName("iban")
    var iban: String? = null,
    @SerializedName("documentsVerified")
    var documentsVerified: Boolean? = false,
    @SerializedName("otpBlocked")
    var otpBlocked: Boolean? = false,
    @SerializedName("termsAndConditionTimeStamp")
    var termsAndConditionTimeStamp: String? = null,
    @SerializedName("csrDocumentTimeStamp")
    var csrDocumentTimeStamp: String? = null,
    @SerializedName("workItemCreated")
    var workItemCreated: Boolean? = false,
    @SerializedName("prepaidAccountNo")
    var prepaidAccountNo: String? = null,
    @SerializedName("emiratesID")
    var emiratesID: String? = null,
    @SerializedName("freezeCode")
    var severityLevel: String? = null,
    @SerializedName("freezeInitiator")
    var freezeInitiator: String? = null,
    @SerializedName("eidNotificationContent")
    var EIDExpiryMessage: String? = null,
    @SerializedName("encryptedAccountUUID")
    var encryptedAccountUUID: String? = null,
    @SerializedName("partnerBankApprovalDate")
    var partnerBankApprovalDate: String? = null,
    @SerializedName("additionalDocSubmitionDate")
    var additionalDocSubmitionDate: String? = null,
    @SerializedName("isWaiting")
    var isWaiting: Boolean = false,
    @SerializedName("amendmentStatus")
    var amendmentStatus: String? = null,
    @SerializedName("kfsAcceptedTimeStamp")
    val kfsAcceptedTimeStamp : String? = null
) :ApiResponse(), Parcelable