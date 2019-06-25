package co.yap.modules.onboarding.interfaces

import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.yapcore.IBase

interface IWelcome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val pages: ArrayList<WelcomeContent>
        fun handlePressOnGetStarted()
    }

    interface State : IBase.State
}