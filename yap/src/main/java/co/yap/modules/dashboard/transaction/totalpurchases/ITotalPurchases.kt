package co.yap.modules.dashboard.transaction.totalpurchases

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.yapcore.IBase

interface ITotalPurchases {
    interface View : IBase.View<ViewModel> {}
    interface ViewModel : IBase.ViewModel<State> {
        val adapter: TransactionsListingAdapter
    }

    interface State : IBase.State {
        var countWithDate: ObservableField<String>
        var position: Int
        var totalSpendings: ObservableField<String>
        var ImageUrl: ObservableField<String>
    }
}