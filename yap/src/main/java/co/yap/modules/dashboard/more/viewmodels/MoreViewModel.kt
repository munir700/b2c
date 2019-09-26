package co.yap.modules.dashboard.more.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.interfaces.IMore
import co.yap.modules.dashboard.more.states.MoreStates
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class MoreViewModel(application: Application) :
    BaseViewModel<IMore.State>(application),
    IMore.ViewModel {

    override val backButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val state: MoreStates =
        MoreStates()

    override fun handlePressOnBackButton() {
        backButtonPressEvent.value = true
    }

    override fun handlePressOnTickButton() {

    }
}