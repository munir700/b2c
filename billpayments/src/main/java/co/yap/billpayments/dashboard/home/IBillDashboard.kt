package co.yap.billpayments.dashboard.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.billpayments.dashboard.home.adapter.DueBillsAdapter
import co.yap.billpayments.dashboard.home.notification.DueBillsNotificationAdapter
import co.yap.billpayments.databinding.FragmentBillDashboardBinding
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillDashboard {
    interface State : IBase.State {
        var showBillCategory: ObservableBoolean
        var totalDueAmount: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillCategoryAdapter
        var clickEvent: SingleClickEvent
        val dueBillsAdapter: DueBillsAdapter
        val notificationAdapter: DueBillsNotificationAdapter
        fun handlePressView(id: Int)
        fun handleBillsResponse(billsList: List<ViewBillModel>?)
    }

    interface View : IBase.View<ViewModel> {
        var isFromSwipePayBill: Boolean
        fun setObservers()
        fun removeObservers()
    }
}
