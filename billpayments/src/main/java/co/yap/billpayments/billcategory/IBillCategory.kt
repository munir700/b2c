package co.yap.billpayments.billcategory

import androidx.databinding.ObservableBoolean
import co.yap.billpayments.billcategory.adapter.BillCategoryAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillCategory {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillCategoryAdapter
        val clickEvent: SingleClickEvent
        fun handlePressView(id: Int)
        fun getBillProviders()
    }

    interface State : IBase.State {
        var dataPopulated: ObservableBoolean
    }
}
