package co.yap.modules.dashboard.cards.cardlist

import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.transaction.search.HomeTransactionAdapter
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.skeletonlayout.Skeleton
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardsList {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var mAdapter: ObservableField<CardListAdapter>
        var mWrappedAdapter: ObservableField<RecyclerView.Adapter<*>>
        var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager
    }

    interface State : IBase.State
}