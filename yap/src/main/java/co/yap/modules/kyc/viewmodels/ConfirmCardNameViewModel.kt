package co.yap.modules.kyc.viewmodels

import android.app.Application
import co.yap.modules.kyc.interfaces.IConfirmCardName
import co.yap.modules.kyc.states.ConfirmCardNameState
import co.yap.yapcore.SingleClickEvent

class ConfirmCardNameViewModel(application: Application) :
    KYCChildViewModel<IConfirmCardName.State>(application),
    IConfirmCardName.ViewModel {
    override val state = ConfirmCardNameState()
    override var clickEvent = SingleClickEvent()
    override fun handlePressOnView(viewId: Int) {
        clickEvent.setValue(viewId)
    }
}
