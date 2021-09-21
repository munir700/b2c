package co.yap.billpayments.dashboard.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class BillDashboardState : BaseState(),
    IBillDashboard.State {
    override var showBillCategory: ObservableBoolean = ObservableBoolean(false)
    override var totalDueAmount: ObservableField<String> = ObservableField("")
}
