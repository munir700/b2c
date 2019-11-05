package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.yapcore.BaseState

class BankDetailsState : BaseState(), IBankDetails.State {

    @get:Bindable
    override var bankName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankName)

        }

    @get:Bindable
    override var swiftCode: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.swiftCode)

        }


    @get:Bindable
    override var bankCity: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankCity)

        }


    @get:Bindable
    override var bankBranch: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.bankBranch)

        }

    fun validate() {
        if (!bankBranch.isNullOrEmpty() && !bankCity.isNullOrEmpty() && !swiftCode.isNullOrEmpty()&& !bankName.isNullOrEmpty()) {
            valid = true
            notifyPropertyChanged(BR.valid)
        }
    }

}