package co.yap.billpayments.paybill.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.databinding.ActivityPayBillMainBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBillMain {
    interface State : IBase.State {
        val toolbarTitleString: ObservableField<String>
        val rightIconVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var onToolbarClickEvent: SingleClickEvent
        fun handlePressView(id: Int)
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): ActivityPayBillMainBinding
    }
}