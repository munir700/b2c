package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.adaptors.PlanBenefitsAdapter
import co.yap.modules.dashboard.store.cardplans.interfaces.IPrimeMetalCard
import co.yap.modules.dashboard.store.cardplans.states.PrimeMetalCardState
import co.yap.yapcore.constants.Constants

class PrimeMetalCardViewModel(application: Application) :
    CardPlansBaseViewModel<IPrimeMetalCard.State>(application), IPrimeMetalCard.ViewModel {
    override val state: PrimeMetalCardState = PrimeMetalCardState()
    override var planBenefitsAdapter: PlanBenefitsAdapter = PlanBenefitsAdapter(arrayListOf(), null)

    override fun onCreate() {
        super.onCreate()
        parentViewModel?.selectedPlan?.get()?.let { planTag ->
            planBenefitsAdapter.setData(getCardBenefits(planTag))
            state.cardPlans.set(getCardPlan(planTag))
        }
    }

    override fun getCardPlan(tag: String): CardPlans? {
        return when (tag) {
            Constants.PRIME_CARD_PLAN -> {
                parentViewModel?.cards?.get(0)
            }
            Constants.METAL_CARD_PLAN -> {
                parentViewModel?.cards?.get(1)
            }
            else -> parentViewModel?.cards?.get(0)
        }
    }

    override fun getCardBenefits(tag: String): MutableList<String> {
        return when (tag) {
            Constants.PRIME_CARD_PLAN -> {
                arrayListOf(
                    "Exclusive partner offers",
                    "2 free virtual cards",
                    "Unlimited multi-currency wallets with real-time exchange rates",
                    "1 free international transfer per month to 40 countries",
                    "Hold up to 3 physical cards",
                    "1 free young subscription",
                    "1 free household subscription",
                    "Priority customer support",
                    "Mastercard Platinum benefits"
                )
            }
            Constants.METAL_CARD_PLAN -> {
                arrayListOf(
                    "Exclusive partner offers",
                    "4 free virtual cards",
                    "Unlimited multi-currency wallets with real-time exchange rates",
                    "1 free international transfer per month to 40 countries",
                    "Hold up to 5 physical cards",
                    "Premier airport lounge access",
                    "2 free young subscription",
                    "2 free household subscription",
                    "Priority customer support",
                    "Travel insurance",
                    "Mastercard World benefits")
            }
            else -> arrayListOf()
        }

    }
}