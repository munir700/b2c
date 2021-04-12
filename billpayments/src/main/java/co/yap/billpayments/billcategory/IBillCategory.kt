package co.yap.billpayments.billcategory

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IBillCategory {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var billcategories: ObservableField<MutableList<BillProviderModel>>
        val clickEvent: SingleClickEvent
        fun handlePressView(id: Int)
        fun getBillProviders()
    }

    interface State : IBase.State {
        var dataPopulated: ObservableBoolean
    }
}
