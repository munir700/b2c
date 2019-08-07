package co.yap.modules.setcardpin.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.interfaces.ISetCardPinSuccess
import co.yap.modules.setcardpin.states.SetCardPinSuccessState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SetCardPinSuccessViewModel(application: Application) : BaseViewModel<ISetCardPinSuccess.State>(application),
    ISetCardPinSuccess.ViewModel {

    override val state: SetCardPinSuccessState = SetCardPinSuccessState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressOnTopUp(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnTopUpLater(id: Int) {
        clickEvent.setValue(id)
    }


}