package co.yap.modules.dashboard.yapit.sendmoney.editbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
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
        }
    @get:Bindable
    override var nickName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.nickName)
        }
    @get:Bindable
    override var firstName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.firstName)
        }
    @get:Bindable
    override var lastName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.lastName)
        }
    @get:Bindable
    override var phoneNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.phoneNumber)
        }
    @get:Bindable
    override var accountNumber: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountNumber)
        }
    @get:Bindable
    override var swiftCode: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.swiftCode)
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
        }
}