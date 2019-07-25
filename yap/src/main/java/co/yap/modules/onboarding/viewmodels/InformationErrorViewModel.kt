package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IInformationError
import co.yap.modules.onboarding.states.InformationErrorState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class InformationErrorViewModel(application: Application) : BaseViewModel<IInformationError.State>(application),
    IInformationError.ViewModel {

    override val state: InformationErrorState = InformationErrorState()
    override var gotoDashboardPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun handlePressOnGoToDashboard() {
        gotoDashboardPressEvent.value = true
    }

}