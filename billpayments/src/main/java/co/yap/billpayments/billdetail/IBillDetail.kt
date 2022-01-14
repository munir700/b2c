package co.yap.billpayments.billdetail

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.IBase

interface IBillDetail {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var rightIconVisibility: ObservableBoolean
        var enableRightIcon: ObservableBoolean
        var leftIconVisibility: ObservableBoolean
        var toolbarTitleString: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        var selectedBill: ViewBillModel?
        var selectedBillPosition: Int?
        fun isPrepaid(): Boolean?
    }

    interface View : IBase.View<ViewModel>
}
