package co.yap.household.onboarding.termandcondition.interfaces

import co.yap.yapcore.IBase

interface ITermAndCondition {

    interface State : IBase.State {
        var toolbarVisibility: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {}
    interface View : IBase.View<ViewModel> {}
}