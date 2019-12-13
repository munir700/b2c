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
           // beneficiary?.beneficiaryType = field
        }
    @get:Bindable
    override var currency: String? = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)
            beneficiary?.currency = field
        }
    @get:Bindable
    override var nickName: String? = null

        set(value) {
            field = value
            notifyPropertyChanged(BR.nickName)
            beneficiary?.title = field
        }


    @get:Bindable
    override var firstName: String? = null

        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
            //notifyChange()
            beneficiary?.firstName = field
        }

    @get:Bindable
    override var lastName: String? = null

        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
            notifyChange()
            beneficiary?.lastName = field
        }
    @get:Bindable
    override var phoneNumber: String? = null

        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)
            beneficiary?.mobileNo = field
        }
    @get:Bindable
    override var accountNumber: String? = null

        set(value) {
            field = value
            notifyPropertyChanged(BR.accountNumber)
            beneficiary?.accountNo = field
        }
    @get:Bindable
    override var swiftCode: String? = null
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
    override var beneficiary: Beneficiary? = Beneficiary()
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
        notifyChange()
    }


}