package co.yap.billpayments.payall.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.billpayments.payall.payallsuccess.adapter.PaidBill
import co.yap.yapcore.IBase

interface IPayAllMain {
    interface State : IBase.State {
        val toolbarTitleString: ObservableField<String>
        val rightIconVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var allBills: MutableLiveData<MutableList<ViewBillModel>>
        var paidBills: MutableList<PaidBill>
    }

    interface View : IBase.View<ViewModel> {
    }
}
