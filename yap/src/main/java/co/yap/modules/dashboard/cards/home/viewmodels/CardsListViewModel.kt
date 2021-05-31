package co.yap.modules.dashboard.cards.home.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.home.interfaces.ICardsList
import co.yap.modules.dashboard.cards.home.states.CardsListState
import co.yap.modules.dashboard.main.viewmodels.YapDashboardChildViewModel
import co.yap.yapcore.SingleClickEvent

class CardsListViewModel(application: Application) :
    YapDashboardChildViewModel<ICardsList.State>(application),
    ICardsList.ViewModel {
    override val clickEvent: SingleClickEvent =  SingleClickEvent()
    override val state: ICardsList.State = CardsListState()
}