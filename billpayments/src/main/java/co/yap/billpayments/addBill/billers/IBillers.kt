package co.yap.billpayments.addBill.billers

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.addBill.billers.adapter.BillersAdapter
import co.yap.billpayments.databinding.FragmentBillersBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory

interface IBillers {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getBindings(): FragmentBillersBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillersAdapter
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getToolbarTitle(billCategory: BillCategory?): String
        fun getScreenTitle(billCategory: BillCategory?): String
        fun getBillerCatalogs(categoryId: String)
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
        var showSearchView: ObservableBoolean
        var valid: ObservableBoolean
    }
}
