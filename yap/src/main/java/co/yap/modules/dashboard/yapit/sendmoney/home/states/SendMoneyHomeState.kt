package co.yap.modules.dashboard.yapit.sendmoney.home.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.yapit.sendmoney.home.interfaces.ISendMoneyHome
import co.yap.yapcore.BaseState

class SendMoneyHomeState : BaseState(), ISendMoneyHome.State {
    override var isNoBeneficiary: ObservableField<Boolean> = ObservableField(false)
    override var isSearching: ObservableField<Boolean> = ObservableField(false)
    override var hasBeneficiary: ObservableField<Boolean> = ObservableField(false)
}