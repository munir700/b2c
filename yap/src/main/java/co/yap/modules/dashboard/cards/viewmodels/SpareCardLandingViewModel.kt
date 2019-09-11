package co.yap.modules.dashboard.cards.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.interfaces.ISpareCards
import co.yap.modules.dashboard.cards.states.SpareCardLandingState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SpareCardLandingViewModel(application: Application) :
    BaseViewModel<ISpareCards.State>(application),
    ISpareCards.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: SpareCardLandingState = SpareCardLandingState()

}