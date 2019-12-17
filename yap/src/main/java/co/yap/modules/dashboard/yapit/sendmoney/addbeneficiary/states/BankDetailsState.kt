package co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.dashboard.yapit.sendmoney.addbeneficiary.interfaces.IBankDetails
import co.yap.yapcore.BaseState

class BankDetailsState : BaseState(), IBankDetails.State {


    @get:Bindable
    override var buttonText: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonText)
            validate()
        }

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
//            validate()
        }

    @get:Bindable
    override var hideSwiftSection: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.hideSwiftSection)
        }

    fun validate() {
        if (!hideSwiftSection) {
            if (bankBranch.isNotEmpty() && bankCity.isNotEmpty() && bankName.isNotEmpty()) {
                valid = true
                notifyPropertyChanged(BR.valid)
                return
            }
        }

        if (!bankBranch.isNullOrEmpty() && !bankCity.isNullOrEmpty() && !swiftCode.isNullOrEmpty() && !bankName.isNullOrEmpty()) {
            valid = true
            notifyPropertyChanged(BR.valid)
        }
    }

}