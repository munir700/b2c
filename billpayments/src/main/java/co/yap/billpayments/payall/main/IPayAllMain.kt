package co.yap.billpayments.payall.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.IBase

interface IPayAllMain {
    interface State : IBase.State {
        val toolbarTitleString: ObservableField<String>
        val rightIconVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel> {
    }
}
