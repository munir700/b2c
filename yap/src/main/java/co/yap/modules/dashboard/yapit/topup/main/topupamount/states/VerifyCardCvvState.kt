package co.yap.modules.dashboard.yapit.topup.main.topupamount.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.yapit.topup.main.topupamount.interfaces.IVerifyCardCvv
import co.yap.yapcore.BaseState

class VerifyCardCvvState : BaseState(), IVerifyCardCvv.State {
    @get:Bindable
    override var cardCvv: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.cardCvv)
        }
}