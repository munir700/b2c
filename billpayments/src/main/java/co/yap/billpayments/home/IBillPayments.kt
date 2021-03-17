package co.yap.billpayments.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.yapcore.IBase

interface IBillPayments {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
        var leftIconVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel>
}