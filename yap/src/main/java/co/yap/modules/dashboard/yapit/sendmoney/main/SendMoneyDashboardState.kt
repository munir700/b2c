package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.BaseState
@Deprecated(message = "Deprecating this class in order to support new interface for addmoneyDashboard",
    replaceWith = ReplaceWith("SendMoneyLinearDashboardState.kt"))
class SendMoneyDashboardState : BaseState(), ISendMoneyDashboard.State {
    override var isRecentsVisible: ObservableBoolean = ObservableBoolean(false)
    override var isNoRecents: ObservableBoolean = ObservableBoolean(true)
}