package co.yap.billpayments.paybill.paybillsuccess

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBillSuccess {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressView(id: Int)
    }

    interface State : IBase.State {
        var paidAmount: ObservableField<String>
        var inputFieldString: ObservableField<String>
    }
}
