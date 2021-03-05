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
    override val state: CardPlansState = CardPlansState()
    override var cardAdapter: CardPlansAdapter = CardPlansAdapter(mutableListOf(), null)
    override fun onCreate() {
        super.onCreate()
        cardAdapter.setData(getCardPlans())
    }

    private fun getCardPlans(): MutableList<CardPlans> {
        return arrayListOf(
            CardPlans(id = Constants.PRIME_CARD_PLAN,
                title = "Prime Card",
                type = "Prime",
                description = "Subscribe to Prime for only AED 8/month and choose your card colour that comes packed with benefits.",
                resource = R.drawable.image_spare_card,
                cardIcon = R.drawable.ic_prime_card_small
            ),
            CardPlans(id = Constants.METAL_CARD_PLAN,
                title = "Metal Card",
                type = "Metal",
                description = "Subscribe to metal starting at AED 50/month and choose a metal card that comes packed with benefits.",
                resource = R.drawable.image_spare_card,
                cardIcon = R.drawable.ic_metal_card_small
            )
        )
    }

}
