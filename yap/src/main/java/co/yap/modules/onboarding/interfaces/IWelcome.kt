package co.yap.modules.onboarding.interfaces

import co.yap.modules.onboarding.enums.AccountType
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.yapcore.IBase

interface IWelcome {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        fun getPages(): ArrayList<WelcomeContent>
        var accountType: AccountType
        fun handlePressOnGetStarted()
    }

    interface State : IBase.State
}