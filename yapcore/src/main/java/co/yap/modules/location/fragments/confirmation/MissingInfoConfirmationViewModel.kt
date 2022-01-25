package co.yap.modules.location.fragments.confirmation

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class MissingInfoConfirmationViewModel(application: Application) :
    BaseViewModel<IMissingInfoConfirmation.State>(application), IMissingInfoConfirmation.ViewModel {

    override val state: IMissingInfoConfirmation.State = MissingInfoConfirmationState()

    override val onClickEvent: SingleClickEvent = SingleClickEvent()

    override fun handlePressView(id: Int) {
        onClickEvent.setValue(id)
    }
}