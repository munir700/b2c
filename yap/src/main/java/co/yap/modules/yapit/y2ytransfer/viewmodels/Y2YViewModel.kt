package co.yap.modules.yapit.y2ytransfer.viewmodels

import android.app.Application
import co.yap.modules.yapit.y2ytransfer.interfaces.IY2Y
import co.yap.modules.yapit.y2ytransfer.states.Y2YState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class Y2YViewModel(application: Application) : BaseViewModel<IY2Y.State>(application),
    IY2Y.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override val state: IY2Y.State =
        Y2YState()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }

    override fun handlePressOnView(id : Int) {

    }
}