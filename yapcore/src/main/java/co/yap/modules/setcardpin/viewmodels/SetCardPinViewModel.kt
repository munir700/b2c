package co.yap.modules.setcardpin.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.interfaces.ISetCardPin
import co.yap.modules.setcardpin.states.SetCardPinState

import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SetCardPinViewModel(application: Application) : BaseViewModel<ISetCardPin.State>(application),
    ISetCardPin.ViewModel {

    override val state: SetCardPinState = SetCardPinState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnNextButton(id: Int) {
        clickEvent.setValue(id)
    }
}