package co.yap.billpayments.mybills

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.mybills.adapter.BillModel
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.yapcore.IBase

interface IMyBills {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: MyBillsAdapter
        var myBills: MutableLiveData<MutableList<BillModel>>
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
    }
}
