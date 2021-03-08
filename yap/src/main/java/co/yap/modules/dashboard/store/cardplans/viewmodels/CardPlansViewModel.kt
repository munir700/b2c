package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.cardplans.CardPlansAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.states.CardPlansState

class CardPlansViewModel(application: Application) :
    CardPlansBaseViewModel<ICardPlans.State>(application), ICardPlans.ViewModel {
    override val state: CardPlansState = CardPlansState()
    override var cardAdapter: CardPlansAdapter = CardPlansAdapter(mutableListOf(), null)
    override fun onCreate() {
        super.onCreate()
        getCards()?.let { cardAdapter.setData(it) }
    }
}
