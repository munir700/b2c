package co.yap.billpayments.dashboard.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.billpayments.dashboard.home.adapter.DueBillsAdapter
import co.yap.billpayments.dashboard.home.notification.DueBillsNotificationAdapter
import co.yap.billpayments.databinding.FragmentPayBillsBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBills {
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
        fun getBillCategoriesApi()
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getBindings(): FragmentPayBillsBinding
    }
}