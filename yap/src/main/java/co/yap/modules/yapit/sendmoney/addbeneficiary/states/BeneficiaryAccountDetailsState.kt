package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.yapcore.BaseState

class BeneficiaryAccountDetailsState : BaseState(), IBeneficiaryAccountDetails.State {

    @get:Bindable
    override var accountIban: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.accountIban)
            validate()
        }

    @get:Bindable
    override var swiftCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.swiftCode)
            validate()
        }


    @get:Bindable
    override var beneficiaryAccountNumber: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryAccountNumber)
            validate()
        }


    @get:Bindable
    override var countryBankRequirementFieldCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.countryBankRequirementFieldCode)
            validate()
        }


    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
            validate()
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