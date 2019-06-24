package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IWelcome
import co.yap.modules.onboarding.states.WelcomeState
import co.yap.yapcore.BaseViewModel

class WelcomeViewModel(application: Application) : BaseViewModel<IWelcome.State>(application), IWelcome.ViewModel {

    override val state: IWelcome.State
        get() = WelcomeState()

    override fun handlePressOnGetStarted() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}