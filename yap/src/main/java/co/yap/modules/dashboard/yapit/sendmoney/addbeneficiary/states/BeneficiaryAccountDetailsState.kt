package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.yapcore.BaseState

class BeneficiaryAccountDetailsState : BaseState(), IBeneficiaryAccountDetails.State {

    override var showlyIban: ObservableField<Boolean> = ObservableField(false)
    override var showlyConfirmIban: ObservableField<Boolean> = ObservableField(false)

    @get:Bindable
    override var accountIban: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountIban)
            validateNonRmt()
        }

    @get:Bindable
    override var accountConfirmIban: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountConfirmIban)
            validateNonRmt()
        }

    @get:Bindable
    override var swiftCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.swiftCode)
        }


    @get:Bindable
    override var beneficiaryAccountNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryAccountNumber)
        }


    @get:Bindable
    override var countryBankRequirementFieldCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.countryBankRequirementFieldCode)
        }


    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }

    private fun validateNonRmt() {
        valid =
            !accountIban.isNullOrEmpty() && !accountConfirmIban.isNullOrEmpty() && accountIban == accountConfirmIban
        notifyPropertyChanged(BR.valid)
    }

    fun validate() {
        if (!countryBankRequirementFieldCode.isNullOrEmpty() && !beneficiaryAccountNumber.isNullOrEmpty() && !swiftCode.isNullOrEmpty() && !accountIban.isNullOrEmpty()) {
            valid = true
            notifyPropertyChanged(BR.valid)
        }
    }

    @get:Bindable
    override var bankName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankName)
        }

    @get:Bindable
    override var idCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.idCode)
        }

    @get:Bindable
    override var bankAddress: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankAddress)
        }
    @get:Bindable
    override var bankPhoneNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankPhoneNumber)
        }
}