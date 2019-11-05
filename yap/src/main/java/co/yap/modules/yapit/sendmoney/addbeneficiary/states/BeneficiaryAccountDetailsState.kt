package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBeneficiaryAccountDetails
import co.yap.yapcore.BaseState

class BeneficiaryAccountDetailsState : BaseState(), IBeneficiaryAccountDetails.State {

    @get:Bindable
    override var bankName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankName)
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
    override var bankCity: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankCity)
            validate()
        }


    @get:Bindable
    override var bankBranch: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankBranch)
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
        if (!bankBranch.isNullOrEmpty() && !bankCity.isNullOrEmpty() && !swiftCode.isNullOrEmpty() && !bankName.isNullOrEmpty()) {
            valid = true
            notifyPropertyChanged(BR.valid)
        }
    }

}