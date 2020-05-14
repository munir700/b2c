package co.yap.modules.dashboard.cards.paymentcarddetail.viewmodels

import android.app.Application
import co.yap.modules.setcardpin.viewmodels.SetCardPinViewModel
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent

open class SetNewPinViewModel(application: Application) : SetCardPinViewModel(application) {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun onCreate() {
        super.onCreate()

    }

    override fun handlePressOnNextButton(id: Int) {
        if (validateAggressively()) {
            clickEvent.setValue(id)
        }
    }

}