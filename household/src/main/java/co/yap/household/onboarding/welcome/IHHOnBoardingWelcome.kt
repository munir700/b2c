package co.yap.household.onboarding.welcome

import co.yap.yapcore.IBase

interface IHHOnBoardingWelcome {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
    }
}
