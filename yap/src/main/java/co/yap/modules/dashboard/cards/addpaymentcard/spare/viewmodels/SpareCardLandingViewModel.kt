package co.yap.modules.dashboard.cards.addpaymentcard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.interfaces.ICards
import co.yap.modules.dashboard.cards.addpaymentcard.states.AddPaymentCardsState
import co.yap.modules.dashboard.cards.addpaymentcard.states.SpareCardLandingState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SpareCardLandingViewModel(application: Application) :
    BaseViewModel<ICards.State>(application),
    ICards.ViewModel {

    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: SpareCardLandingState =
        SpareCardLandingState()

}