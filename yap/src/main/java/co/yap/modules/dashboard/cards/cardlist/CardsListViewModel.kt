package co.yap.modules.dashboard.cards.cardlist

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.yapcore.SingleClickEvent

class CardsListViewModel(application: Application) :
    YapDashboardChildViewModel<ICardsList.State>(application),
    ICardsList.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ICardsList.State = CardsListState()
    override var mAdapter: ObservableField<CardListAdapter> = ObservableField()
    override var mWrappedAdapter: ObservableField<RecyclerView.Adapter<*>> = ObservableField()
    override var mRecyclerViewExpandableItemManager: RecyclerViewExpandableItemManager =
        RecyclerViewExpandableItemManager(null)
}