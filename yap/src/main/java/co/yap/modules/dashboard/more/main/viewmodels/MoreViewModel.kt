package co.yap.modules.dashboard.more.main.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.main.interfaces.IMore
import co.yap.modules.dashboard.more.main.states.MoreStates
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