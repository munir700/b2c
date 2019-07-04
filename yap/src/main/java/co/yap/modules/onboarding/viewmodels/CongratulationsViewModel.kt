package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.ICongratulations
import co.yap.modules.onboarding.states.CongratulationsState
import co.yap.yapcore.BaseViewModel

class CongratulationsViewModel(application: Application) : BaseViewModel<ICongratulations.State>(application),
    ICongratulations.ViewModel {

    override val state: CongratulationsState = CongratulationsState()


    override fun handlePressOnCompleteVerification() {

    }


}