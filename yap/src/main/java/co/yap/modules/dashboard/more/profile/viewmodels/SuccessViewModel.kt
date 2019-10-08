package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.ISuccess
import co.yap.modules.dashboard.more.profile.states.SuccessState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class SuccessViewModel(application: Application) :
    BaseViewModel<ISuccess.State>(application), ISuccess.ViewModel {
    override val buttonClickEvent: SingleClickEvent = SingleClickEvent()
    override val state: SuccessState = SuccessState()

    override fun handlePressOnDoneButton() {
        buttonClickEvent.call()
    }
}