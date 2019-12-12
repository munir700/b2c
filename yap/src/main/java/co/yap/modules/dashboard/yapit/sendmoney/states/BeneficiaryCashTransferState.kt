package co.yap.modules.dashboard.yapit.sendmoney.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.IBeneficiaryCashTransfer
import co.yap.yapcore.BaseState

class BeneficiaryCashTransferState : BaseState(), IBeneficiaryCashTransfer.State {

    @get:Bindable
    override var toolBarTitle: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.toolBarTitle)
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


}