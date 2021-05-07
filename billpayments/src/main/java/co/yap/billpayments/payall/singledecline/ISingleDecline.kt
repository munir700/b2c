package co.yap.billpayments.payall.singledecline

import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.FragmentSingleDeclineBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISingleDecline {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): FragmentSingleDeclineBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handleOnPressView(id: Int)
    }

    interface State : IBase.State {
        var paidAmount: ObservableField<String>
    }
}
