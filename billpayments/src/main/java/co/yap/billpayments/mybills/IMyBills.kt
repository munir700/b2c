package co.yap.billpayments.mybills

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.mybills.adapter.BillModel
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.interfaces.OnItemClickListener

interface IMyBills {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
        val onItemClickListener: OnItemClickListener
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: MyBillsAdapter
        var myBills: MutableLiveData<MutableList<BillModel>>
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
    }
}
