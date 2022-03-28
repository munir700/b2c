package co.yap.modules.dashboard.yapit.sendmoney.main

import androidx.databinding.ObservableBoolean
import co.yap.databinding.ActivitySendMoneyDashboardBinding
import co.yap.modules.dashboard.yapit.sendmoney.landing.SendMoneyLinearDashboardAdapter
import co.yap.networking.customers.responsedtos.sendmoney.Beneficiary
import co.yap.widgets.recent_transfers.CoreRecentTransferAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.permissions.PermissionHelper

interface ISendMoneyLinearDashboard {
    interface State : IBase.State {
        var isRecentsVisible: ObservableBoolean
        var isNoRecents: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var recentTransfers: ArrayList<Beneficiary>
        var dashboardAdapter: SendMoneyLinearDashboardAdapter
        var recentsAdapter: CoreRecentTransferAdapter
        val clickEvent: SingleClickEvent
        fun geSendMoneyOptions(): MutableList<SendMoneyLinearOptions>
        fun handlePressOnView(id: Int)
        fun getAllRecentsBeneficiariesParallel()
    }

    interface View : IBase.View<ViewModel> {
        var permissionHelper: PermissionHelper?
        fun getBinding(): ActivitySendMoneyDashboardBinding
        fun setObservers()
        fun removeObservers()
    }
}
