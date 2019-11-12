package co.yap.modules.yapit.sendmoney.addbeneficiary.states

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import co.yap.modules.yapit.sendmoney.addbeneficiary.interfaces.IInternationalFundsTransfer
import co.yap.yapcore.BaseState

class InternationalFundsTransferState : BaseState(), IInternationalFundsTransfer.State {


    @get:Bindable
    override var senderCurrency: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.senderCurrency)
        }

    @get:Bindable
    override var beneficiaryCurrency: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryCurrency)
        }

    @get:Bindable
    override var beneficiaryCountry: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryCountry)
        }

    @get:Bindable
    override var senderAmount: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.senderAmount)
        }

    @get:Bindable
    override var beneficiaryAmount: String = "AED"
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiaryAmount)
        }

    @get:Bindable
    override var valid: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.valid)
        }
}