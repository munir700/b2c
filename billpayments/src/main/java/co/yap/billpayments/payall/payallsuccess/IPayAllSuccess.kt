package co.yap.billpayments.payall.payallsuccess

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import co.yap.billpayments.payall.payallsuccess.adapter.PayAllBillsAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayAllSuccess {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adapter: PayAllBillsAdapter
        fun handleOnPressView(id: Int)
        fun getToolbarTitle(): String
        fun setScreenTitle()
        fun getSuccessfullyPaidBills(): Int?
    }

    interface State : IBase.State {
        var paidAmount: ObservableField<String>
        var billsPaid: ObservableInt
        var screenTitle: ObservableField<String>
    }
}
