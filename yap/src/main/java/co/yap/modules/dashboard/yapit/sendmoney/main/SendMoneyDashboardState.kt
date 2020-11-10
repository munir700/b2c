package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.yapcore.BaseState

class SendMoneyDashboardState : BaseState(), ISendMoneyDashboard.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean(true)
    override var rightIconVisibility: ObservableBoolean = ObservableBoolean(true)
    override var leftIconVisibility: ObservableBoolean = ObservableBoolean(false)
    override var rightButtonText: ObservableField<String> = ObservableField("")
    override var rightButtonTextVisibility: ObservableBoolean = ObservableBoolean(false)

    @get:Bindable
    override var labelTextVisibility: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.labelTextVisibility)
        }
}