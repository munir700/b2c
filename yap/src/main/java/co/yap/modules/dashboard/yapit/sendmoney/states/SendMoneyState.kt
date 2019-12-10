package co.yap.modules.dashboard.yapit.sendmoney.states

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.countryutils.country.Country
import co.yap.modules.dashboard.yapit.sendmoney.interfaces.ISendMoney
import co.yap.yapcore.BaseState

class SendMoneyState : BaseState(), ISendMoney.State {

    override var rightIcon: ObservableBoolean = ObservableBoolean(false)
    override var leftIcon: ObservableBoolean = ObservableBoolean(false)
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(false)
}