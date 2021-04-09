package co.yap.billpayments.billers.search

import androidx.databinding.ObservableBoolean
import co.yap.billpayments.billers.adapter.BillersAdapter
import co.yap.networking.customers.responsedtos.billpayment.BillerCatalogModel
import co.yap.yapcore.IBase

interface IBillerSearch {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        var adapter: BillersAdapter
        var billerCatalogs: MutableList<BillerCatalogModel>
    }

    interface State : IBase.State {
        var nextButtonEnabled: ObservableBoolean
    }
}
