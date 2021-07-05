package co.yap.billpayments.payall.home

import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentPayAllBinding
import co.yap.billpayments.payall.payallsuccess.adapter.PayAllBillsAdapter
import co.yap.billpayments.payall.payallsuccess.adapter.PaidBill
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayAll {

    interface State : IBase.State {
        var totalAmount: ObservableField<String>
        var availableBalanceString: ObservableField<CharSequence>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adapter: PayAllBillsAdapter
        fun handleOnPressView(id: Int)
        fun populateData()
        fun payAllBills(success: () -> Unit)
        fun readFromFile(): List<PaidBill>
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentPayAllBinding
    }
}
