package co.yap.household.onboard.onboarding.kycsuccess

import co.yap.yapcore.IBase

interface IKycSuccess {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State>

    interface State : IBase.State
}