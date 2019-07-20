package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IInvalidCountry
import co.yap.modules.onboarding.states.InvalidCountryState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent

class InvalidCountryViewModel(application: Application) : BaseViewModel<IInvalidCountry.State>(application),
    IInvalidCountry.ViewModel {
    override val state: InvalidCountryState = InvalidCountryState()
    override var gotoDashboardPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun handlePressOnGoToDashboard() {
        gotoDashboardPressEvent.value = true
    }

}