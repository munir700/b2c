package co.yap.modules.dashboard.cards.analytics.interfaces

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.adaptors.CardAnalyticsDetailsAdapter
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.networking.transactions.responsedtos.TxnAnalytic
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardAnalyticsDetails {
    interface View : IBase.View<ViewModel> {

    }
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent?
        fun handleOnClickEvent(id: Int)
        val adapter: ObservableField<TransactionsListingAdapter>
        fun getCardAnalyticsDetails()

    }
    interface State : IBase.State {
        var title : ObservableField<String>
        var totalSpendings : ObservableField<String>
        var transactionCount : ObservableField<String>
        var date : ObservableField<String>
        var ImageUrl : ObservableField<String>
    }
}