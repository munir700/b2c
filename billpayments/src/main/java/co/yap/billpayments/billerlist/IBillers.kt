package co.yap.billpayments.billerlist

import androidx.databinding.ObservableField
import co.yap.billpayments.billerlist.adapter.BillerModel
import co.yap.billpayments.billerlist.adapter.BillersAdapter
import co.yap.yapcore.IBase

interface IBillers {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillersAdapter
        var billers: MutableList<BillerModel>
        fun getBillerList(): MutableList<BillerModel>
    }

    interface State : IBase.State {
        var screenTitle: ObservableField<String>
    }
}
