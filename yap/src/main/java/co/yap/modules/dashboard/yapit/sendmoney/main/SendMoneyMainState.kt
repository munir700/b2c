package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class SendMoneyMainState : BaseState(), ISendMoneyMain.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var rightIconVisibility: ObservableBoolean = ObservableBoolean(true)
    override var leftIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var rightButtonText: ObservableField<String> = ObservableField("")
}