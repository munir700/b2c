package co.yap.household.dashboard.cards

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.household.dashboard.home.HomeTransactionAdapter
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.networking.transactions.requestdtos.HomeTransactionsRequest
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IMyCard {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_FREEZE_UNFREEZE_CARD: Int get() = 1
        val EVENT_CARD_DETAILS: Int get() = 2
        val clickEvent: SingleClickEvent
        fun freezeUnfreezeCard(success: () -> Unit)
        fun getCardDetails(success: () -> Unit)
        fun handlePressOnButtonClick(id: Int)
        fun getPrimaryCard(success: () -> Unit)
        fun requestTransactions(
            transactionRequest: HomeTransactionsRequest?,
            isLoadMore: Boolean = false,
            apiResponse: ((co.yap.widgets.State) -> Unit?)? = null
        )

        val transactionAdapter: ObservableField<HomeTransactionAdapter>?
        fun getPaginationListener(): PaginatedRecyclerView.Pagination?
    }

    interface State : IBase.State {
        var card: MutableLiveData<Card>?
        var cardStatus: MutableLiveData<String?>
        var cardDetail: MutableLiveData<CardDetail>?
        var transactionRequest: HomeTransactionsRequest?
        val transactionMap: MutableLiveData<Map<String?, List<Transaction>>>?
    }
}
