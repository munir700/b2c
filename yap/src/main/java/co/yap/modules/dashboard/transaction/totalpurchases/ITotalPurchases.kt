package co.yap.modules.dashboard.transaction.totalpurchases

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.networking.transactions.requestdtos.TotalPurchaseRequest
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.CoreCircularImageView
import co.yap.yapcore.IBase

interface ITotalPurchases {
    interface View : IBase.View<ViewModel> {}
    interface ViewModel : IBase.ViewModel<State> {
        val adapter: TransactionsListingAdapter
        var transaction: ObservableField<Transaction>
        fun getTotalPurchaseList()
        fun getTotalPurchaseRequest():TotalPurchaseRequest
        fun setMerchantImage(coreCircularImageView: CoreCircularImageView)
    }

    interface State : IBase.State {
        var countWithDate: ObservableField<String>
        var totalSpendings: ObservableField<String>
        var merchantName: ObservableField<String>
    }
}