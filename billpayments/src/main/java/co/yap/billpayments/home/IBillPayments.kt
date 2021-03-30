package co.yap.billpayments.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.billpayment.BillProviderModel
import co.yap.networking.customers.responsedtos.billpayment.BillerModel
import co.yap.yapcore.IBase

interface IBillPayments {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
        var leftIconVisibility: ObservableBoolean
        var toolbarTitleString: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var billers: MutableList<BillerModel>
        var selectedBillProvider: BillProviderModel?
    }

    interface View : IBase.View<ViewModel>
}
