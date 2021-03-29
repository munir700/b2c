package co.yap.billpayments.billers

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.billpayment.BillerModel
import co.yap.billpayments.billers.adapter.BillersAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory

interface IBillers {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillersAdapter
        var billers: MutableList<BillerModel>
        fun getBillerList(): MutableList<BillerModel>
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        fun getToolbarString(billCategory: BillCategory?): String
        fun getScreenTitle(billCategory: BillCategory?): String
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var showSearchView: ObservableBoolean
        var valid: ObservableBoolean
    }
}
