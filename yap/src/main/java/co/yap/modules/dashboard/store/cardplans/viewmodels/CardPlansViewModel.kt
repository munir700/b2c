package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import co.yap.modules.dashboard.store.cardplans.adaptors.CardPlansAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.states.CardPlansState
import co.yap.yapcore.helpers.Utils

class CardPlansViewModel(application: Application) :
    CardPlansBaseViewModel<ICardPlans.State>(application), ICardPlans.ViewModel {
    override val state: CardPlansState = CardPlansState()
    override var cardAdapter: CardPlansAdapter = CardPlansAdapter(mutableListOf(), null)

    override fun onCreate() {
        super.onCreate()
        getCards()?.let { cardAdapter.setData(it) }
    }
}
