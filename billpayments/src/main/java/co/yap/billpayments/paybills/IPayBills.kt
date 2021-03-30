package co.yap.billpayments.paybills

import androidx.databinding.ObservableField
import co.yap.billpayments.paybills.adapter.DueBillsAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBills {
    interface State : IBase.State {
        var showBillCategory: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val dueBillsAdapter: DueBillsAdapter
        var billcategories: ObservableField<MutableList<BillProviderModel>>
        fun handlePressView(id: Int)
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }
}