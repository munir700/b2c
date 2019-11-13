package co.yap.modules.dashboard.yapit.topup.main.topupamount.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.ITopUpCardSuccess
import co.yap.yapcore.BaseState

class TopUpCardSuccessState : BaseState(), ITopUpCardSuccess.State {
    @get:Bindable
    override var buttonTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.buttonTitle)
        }
}