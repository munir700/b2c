package co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.interfaces.IEditBeneficiary
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseState

class EditBeneficiaryStates : BaseState(), IEditBeneficiary.State {
    @get:Bindable
    override var country: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.country)
        }
    @get:Bindable
    override var transferType: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.transferType)
        }
    @get:Bindable
    override var currency: String? = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)
            beneficiary?.currency = field
        }
    @get:Bindable
    override var nickName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.nickName)
            beneficiary?.title = field
        }


    @get:Bindable
    override var firstName: String?=null
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            beneficiary?.firstName = field
        }
    @get:Bindable
    override var lastName: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
            beneficiary?.lastName = field
        }
    @get:Bindable
    override var phoneNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)
            beneficiary?.mobileNo = field
        }
    @get:Bindable
    override var accountNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountNumber)
            beneficiary?.accountNo = field
        }
    @get:Bindable
    override var swiftCode: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.swiftCode)
            beneficiary?.swiftCode = field
        }
    @get:Bindable
    override var countryBankRequirementFieldCode: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.countryBankRequirementFieldCode)
        }
    @get:Bindable
    override var beneficiary: Beneficiary? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiary)
            updateAllFields(beneficiary)
        }

    private fun updateAllFields(beneficiary: Beneficiary?) {

        beneficiary?.let { it ->
            it.country?.let { country = it}
            it.lastName?.let { lastName = it}
            it.title?.let { nickName = it}
            it.firstName?.let { firstName = it}
            it.mobileNo?.let { phoneNumber = it}
            it.accountNo?.let { accountNumber = it}
            it.swiftCode?.let { swiftCode = it}
            it.beneficiaryType?.let { transferType = it}
        }
////A we are not sure about updated response so placing these checks in order to avoid crash
//
//        if (!beneficiary.country.isNullOrEmpty()) country = beneficiary.country!!
//        if (!beneficiary.beneficiaryType.isNullOrEmpty()) transferType =
//            beneficiary.beneficiaryType!!
//        if (!beneficiary.currency.isNullOrEmpty()) currency = beneficiary.currency!!
//        if (!beneficiary.title.isNullOrEmpty()) nickName = beneficiary.title!!
//        if (!beneficiary.firstName.isNullOrEmpty()) firstName = beneficiary.firstName!!
//        if (!beneficiary.lastName.isNullOrEmpty()) lastName = beneficiary.lastName!!
//        if (!beneficiary.mobileNo.isNullOrEmpty()) phoneNumber = beneficiary.mobileNo!!
//        if (!beneficiary.mobileNo.isNullOrEmpty()) mobile = beneficiary.mobileNo!!
//        if (!beneficiary.accountNo.isNullOrEmpty()) accountIban = beneficiary.accountNo!!
//        if (!beneficiary.mobileNo.isNullOrEmpty()) swiftCode = beneficiary.swiftCode!!
//
//// now again not sure about required field including bank details, so consider it to work on...
//
//        if (!beneficiary.mobileNo.isNullOrEmpty()) countryBankRequirementFieldCode =
//            beneficiary.country!!

    }
}