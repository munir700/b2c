package co.yap.modules.location.kyc_additional_info.cardontheway

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CardOnTheWayViewModel(application: Application) :
    BaseViewModel<ICardOnTheWay.State>(application),
    ICardOnTheWay.ViewModel {
    override val state: ICardOnTheWay.State =
        CardOnTheWayState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}