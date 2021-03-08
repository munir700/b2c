package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.R
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.interfaces.IMainCardPlans
import co.yap.modules.dashboard.store.cardplans.states.CardPlansMainState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.constants.Constants

class CardPlansMainViewModel(application: Application) :
    BaseViewModel<IMainCardPlans.State>(application), IMainCardPlans.ViewModel {
    override var cards: MutableList<CardPlans> = arrayListOf()

    override val state: CardPlansMainState = CardPlansMainState()
    override fun onCreate() {
        super.onCreate()
        cards.addAll(getCardPlans())
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