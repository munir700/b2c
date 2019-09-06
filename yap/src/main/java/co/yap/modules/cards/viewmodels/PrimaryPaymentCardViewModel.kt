package co.yap.modules.cards.viewmodels

import android.app.Application
import co.yap.modules.cards.interfaces.IPrimaryPaymentCard
import co.yap.modules.cards.states.PrimaryPaymentCardState
import co.yap.yapcore.BaseViewModel

class PrimaryPaymentCardViewModel(application: Application) :
    BaseViewModel<IPrimaryPaymentCard.State>(application), IPrimaryPaymentCard.ViewModel {
    override val state: PrimaryPaymentCardState = PrimaryPaymentCardState()


}