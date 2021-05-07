package co.yap.billpayments.payall.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.IBase

interface IPayAllMain {
    interface State : IBase.State {
        val toolbarTitleString: ObservableField<String>
        val rightIconVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var allBills: MutableList<ViewBillModel>
    }

    interface View : IBase.View<ViewModel> {
    }
}
