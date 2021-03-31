package co.yap.billpayments.paybills

import co.yap.billpayments.databinding.FragmentPayBillsBinding
import co.yap.billpayments.databinding.LayoutBillPaymentsDueBinding
import co.yap.billpayments.paybills.adapter.DueBillsAdapter
import co.yap.billpayments.paybills.notification.DueBillsNotificationAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBills {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val dueBillsAdapter: DueBillsAdapter
        val notificationAdapter: DueBillsNotificationAdapter
        fun handlePressView(id: Int)
    }

    interface View : IBase.View<ViewModel>{
        fun setObservers()
        fun removeObservers()
        fun getBindings(): FragmentPayBillsBinding
    }
}