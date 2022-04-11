package co.yap.billpayments.payall.singledecline

import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentSingleDeclineBinding
import co.yap.billpayments.payall.payallsuccess.adapter.PaidBill
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISingleDecline {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var declinedBill: ObservableField<PaidBill>
        fun handleOnPressView(id: Int)
    }

    interface State : IBase.State {
        var paidAmount: ObservableField<String>
    }
}
