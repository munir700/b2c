package co.yap.household.onboarding.termandcondition.`interface`

import co.yap.yapcore.IBase

interface ITermAndCondition {
    interface State : IBase.State {

    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface View : IBase.View<ViewModel>
}