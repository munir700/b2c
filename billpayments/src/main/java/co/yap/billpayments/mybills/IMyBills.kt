package co.yap.billpayments.mybills

import androidx.databinding.ObservableField
import co.yap.billpayments.mybills.adapter.MyBillsAdapter
import co.yap.billpayments.mybills.adapter.MyBillsModel
import co.yap.yapcore.IBase

interface IMyBills {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: MyBillsAdapter
        var myBills: MutableList<MyBillsModel>
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
    }
}
