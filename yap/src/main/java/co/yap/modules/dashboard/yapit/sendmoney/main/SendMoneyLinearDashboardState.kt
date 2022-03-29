package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.ObservableBoolean
import co.yap.yapcore.BaseState

class SendMoneyLinearDashboardState : BaseState(), ISendMoneyLinearDashboard.State {
    override var isRecentsVisible: ObservableBoolean = ObservableBoolean(false)
    override var isNoRecents: ObservableBoolean = ObservableBoolean(true)
}