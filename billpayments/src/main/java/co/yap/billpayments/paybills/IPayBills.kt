package co.yap.billpayments.paybills

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentPayBillsBinding
import co.yap.billpayments.paybills.adapter.DueBillsAdapter
import co.yap.billpayments.paybills.notification.DueBillsNotificationAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBills {
    interface State : IBase.State {
        var showBillCategory: ObservableBoolean
        var totalDueAmount: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val dueBillsAdapter: DueBillsAdapter
        val notificationAdapter: DueBillsNotificationAdapter
        var billcategories: ObservableField<MutableList<BillProviderModel>>
        fun handlePressView(id: Int)
        fun getBillCategoriesApi()
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getBindings(): FragmentPayBillsBinding
    }
}