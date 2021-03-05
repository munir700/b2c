package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.CardPlansAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.ICardPlans
import co.yap.modules.dashboard.store.cardplans.states.CardPlansState
import co.yap.yapcore.constants.Constants

class CardPlansViewModel(application: Application) :
    CardPlansBaseViewModel<ICardPlans.State>(application), ICardPlans.ViewModel {
    override val state: ICardPlans.State = CardPlansState()
    override var cardAdapter: CardPlansAdapter = CardPlansAdapter(mutableListOf(), null)
    override fun onCreate() {
        super.onCreate()
        cardAdapter.setData(getCardPlans())
    }

    private fun getCardPlans(): MutableList<CardPlans> {
        return arrayListOf(
            CardPlans(id = Constants.PRIME_CARD_PLAN,
                title = "Prime Card",
                resourse = R.drawable.image_spare_card),
            CardPlans(id = Constants.METAL_CARD_PLAN,
                title = "Metal Card",
                resourse = R.drawable.image_spare_card)
        )
    }

}
