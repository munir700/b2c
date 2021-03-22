package co.yap.billpayments.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.billpayments.billers.adapter.BillerModel
import co.yap.yapcore.IBase
import co.yap.yapcore.enums.BillCategory

interface IBillPayments {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
        var leftIconVisibility: ObservableBoolean
        var toolbarTitleString: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State>{
        var billers: MutableList<BillerModel>
        var selectedBillCategory: BillCategory?
    }

    interface View : IBase.View<ViewModel>
}
