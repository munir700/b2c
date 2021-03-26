package co.yap.billpayments.billers.search

import androidx.databinding.ObservableBoolean
import co.yap.billpayments.billers.adapter.BillerModel
import co.yap.billpayments.billers.adapter.BillersAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillerSearch {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillersAdapter
        var billers: MutableList<BillerModel>
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State {
        var nextButtonEnabled: ObservableBoolean
    }
}
