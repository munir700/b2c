package co.yap.billpayments.paybill.paybillsuccess

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentPayBillSuccessBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBillSuccess {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentPayBillSuccessBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressView(id: Int)
        fun getToolbarTitle(): String
    }

    interface State : IBase.State {
        var paidAmount: ObservableField<String>
        var inputFieldString: ObservableField<String>
        var isSuccessful: ObservableBoolean
    }
}
