package co.yap.billpayments.paybill.main

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.databinding.ActivityPayBillMainBinding
import co.yap.networking.customers.responsedtos.billpayment.ViewBillModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPayBillMain {
    interface State : IBase.State {
        val toolbarTitleString: ObservableField<String>
        val rightIconVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var errorEvent: MutableLiveData<String>
        val billModel: MutableLiveData<ViewBillModel>
    }

    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        fun getViewBinding(): ActivityPayBillMainBinding
    }
}