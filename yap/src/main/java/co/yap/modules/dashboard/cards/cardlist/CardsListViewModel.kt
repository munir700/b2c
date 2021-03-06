package co.yap.modules.dashboard.cards.cardlist

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.yapcore.SingleClickEvent

class CardsListViewModel(application: Application) :
    YapDashboardChildViewModel<ICardsList.State>(application),
    ICardsList.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val cardAdapter: ObservableField<CardListAdapter>? = ObservableField()
    override val state: ICardsList.State = CardsListState()
}