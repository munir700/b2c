package co.yap.billpayments.paybill

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentPayBillBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBill {
    interface State : IBase.State {
        var availableBalanceString: ObservableField<CharSequence>
        var noteValue: ObservableField<String>
        var isAutoPaymentOn: ObservableBoolean?
        var isBillReminderOn: Boolean?
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressView(id: Int)
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentPayBillBinding
    }
}