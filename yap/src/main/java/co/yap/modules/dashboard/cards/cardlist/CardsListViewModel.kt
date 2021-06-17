package co.yap.modules.dashboard.cards.cardlist

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.RecyclerView
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.modules.dashboard.transaction.search.HomeTransactionAdapter
import co.yap.networking.cards.responsedtos.Card
import co.yap.widgets.Status
import co.yap.widgets.advrecyclerview.expandable.RecyclerViewExpandableItemManager
import co.yap.widgets.advrecyclerview.pagination.PaginatedRecyclerView
import co.yap.yapcore.SingleClickEvent

class CardsListViewModel(application: Application) :
    YapDashboardChildViewModel<ICardsList.State>(application),
    ICardsList.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val cardAdapter: ObservableField<CardListAdapter>? = ObservableField()
    override val state: ICardsList.State = CardsListState()


    override fun onCreate() {
        super.onCreate()
    }
}