package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class SendMoneyDashboardState : BaseState(), ISendMoneyDashboard.State {
    override var isRecentsVisible: ObservableBoolean = ObservableBoolean(false)
    override var isNoRecents: ObservableBoolean = ObservableBoolean(true)
}