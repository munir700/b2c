package co.yap.billpayments.payall.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.databinding.FragmentPayAllBinding
import co.yap.billpayments.payall.payallsuccess.adapter.PaidBill
import co.yap.billpayments.payall.payallsuccess.adapter.PayAllBillsAdapter
import co.yap.networking.transactions.requestdtos.PayAllRequest
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayAll {

    interface State : IBase.State {
        var totalAmount: ObservableField<String>
        var availableBalanceString: ObservableField<CharSequence>
        var allBillsToBePaid: List<PaidBill>?
        val valid: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var errorEvent: MutableLiveData<String>
        var clickEvent: SingleClickEvent
        var adapter: PayAllBillsAdapter
        fun handleOnPressView(id: Int)
        fun populateData()
        fun payAllBills(
            payAllRequest: ArrayList<PayAllRequest>,
            success: () -> Unit
        )

        fun isBalanceAvailable(enterAmount: Double): Boolean
        fun getPayAllBillsRequest(): ArrayList<PayAllRequest>
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentPayAllBinding
    }
}
