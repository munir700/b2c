package co.yap.modules.yapit.y2ytransfer.viewmodels

import android.app.Application
import co.yap.modules.yapit.y2ytransfer.interfaces.IY2Y
import co.yap.modules.yapit.y2ytransfer.states.Y2YState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class Y2YViewModel(application: Application) : BaseViewModel<IY2Y.State>(application),
    IY2Y.ViewModel {

    override var clickEvent: SingleClickEvent = SingleClickEvent()

    override val state: Y2YState =
        Y2YState()

    override fun handlePressOnBackButton(id: Int) {
        clickEvent.setValue(id)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)

    }
}