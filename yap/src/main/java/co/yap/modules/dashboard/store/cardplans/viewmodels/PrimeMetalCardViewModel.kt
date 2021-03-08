package co.yap.modules.dashboard.store.cardplans.viewmodels

import android.app.Application
import co.yap.modules.dashboard.store.cardplans.interfaces.IPrimeMetalCard
import co.yap.modules.dashboard.store.cardplans.states.PrimeMetalCardState

class PrimeMetalCardViewModel(application: Application) :
    CardPlansBaseViewModel<IPrimeMetalCard.State>(application), IPrimeMetalCard.ViewModel {
    override val state: PrimeMetalCardState = PrimeMetalCardState()
}