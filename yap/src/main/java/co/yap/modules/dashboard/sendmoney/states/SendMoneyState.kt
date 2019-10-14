package co.yap.modules.dashboard.sendmoney.states

import androidx.databinding.Bindable
import co.yap.BR
import co.yap.modules.dashboard.sendmoney.interfaces.ISendMoney
import co.yap.yapcore.BaseState

class SendMoneyState : BaseState(), ISendMoney.State {

    @get:Bindable
    override var tootlBarTitle: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarTitle)

        }

    @get:Bindable
    override var tootlBarVisibility: Int = 0x00000000
        set(value) {
            field = value
            notifyPropertyChanged(BR.tootlBarVisibility)

        }
}