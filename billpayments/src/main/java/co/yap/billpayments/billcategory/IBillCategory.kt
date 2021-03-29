package co.yap.billpayments.billcategory

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillCategory {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var billcategories: ObservableField<MutableList<BillCategoryModel>>
        fun handlePressView(id: Int)
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State
}
