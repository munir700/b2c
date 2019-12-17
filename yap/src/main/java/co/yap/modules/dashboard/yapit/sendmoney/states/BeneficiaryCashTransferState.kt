package co.yap.modules.dashboard.yapit.sendmoney.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.yapcore.BaseState

class BeneficiaryCashTransferState : BaseState(), IBeneficiaryCashTransfer.State {

    @get:Bindable
    override var toolBarTitle: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarTitle)
        }

    @get:Bindable
    override var otpSuccess: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.otpSuccess)
        }
    @get:Bindable
    override var beneficiary: Beneficiary? = Beneficiary()
        set(value) {
            field = value
            notifyPropertyChanged(BR.beneficiary)
        }


    @get:Bindable
    override var toolBarVisibility: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarVisibility)
        }
    @get:Bindable
    override var leftButtonVisibility: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.leftButtonVisibility)
        }

    @get:Bindable
    override var rightButtonVisibility: Boolean? = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.rightButtonVisibility)
        }
    @get:Bindable
    override var rightButtonText: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.rightButtonText)
        }

    @get:Bindable
    override var position: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.position)
        }
}