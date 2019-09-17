package co.yap.modules.dashboard.cards.addpaymentcard.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.addpaymentcard.interfaces.IAddPaymentCard
import co.yap.modules.dashboard.cards.addpaymentcard.states.AddPaymentCardsState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class AddPaymentCardViewModel(application: Application) :
    BaseViewModel<IAddPaymentCard.State>(application),
    IAddPaymentCard.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: AddPaymentCardsState =
        AddPaymentCardsState()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }
}