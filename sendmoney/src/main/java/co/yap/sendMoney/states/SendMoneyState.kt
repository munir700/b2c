package co.yap.sendmoney.states

import androidx.databinding.ObservableBoolean
import co.yap.sendmoney.interfaces.ISendMoney
import co.yap.yapcore.BaseState

class SendMoneyState : BaseState(), ISendMoney.State {

    override var rightIcon: ObservableBoolean = ObservableBoolean(false)
    override var leftIcon: ObservableBoolean = ObservableBoolean(false)
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(false)
}