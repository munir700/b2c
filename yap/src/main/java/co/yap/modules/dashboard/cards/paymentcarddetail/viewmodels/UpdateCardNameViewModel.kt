package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.paymentcarddetail.interfaces.IUpdateCardName
import co.yap.modules.dashboard.cards.paymentcarddetail.states.UpdateCardNameState
import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class UpdateCardNameViewModel (application: Application) :
    BaseViewModel<IUpdateCardName.State>(application),
    IUpdateCardName.ViewModel {


    override val state: UpdateCardNameState = UpdateCardNameState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override lateinit var card: Card

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun updateCardName() {
    }

}
