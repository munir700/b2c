package co.yap.modules.dashboard.cards.cardlist

import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.transaction.search.HomeTransactionAdapter
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.widgets.skeletonlayout.Skeleton
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardsList {
    interface View : IBase.View<ViewModel>{
        val EVENT_PAYMENT_CARD_DETAIL: Int get() = 11
    }
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val cardAdapter: ObservableField<CardListAdapter>?
    }

    interface State : IBase.State{
        var cardMap : MutableMap<String?, List<Card>>
    }
}