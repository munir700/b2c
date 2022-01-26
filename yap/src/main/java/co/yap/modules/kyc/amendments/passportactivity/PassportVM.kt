package co.yap.modules.kyc.amendments.passportactivity

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class PassportVM(application: Application) : BaseViewModel<IPassport.State>(application),
    IPassport.ViewModel {
    override val state: PassportState = PassportState()
    override val clickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}