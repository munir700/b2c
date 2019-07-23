package co.yap.modules.onboarding.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IInformationError {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var gotoDashboardPressEvent: SingleLiveEvent<Boolean>
        fun handlePressOnGoToDashboard()

    }

    interface State : IBase.State
}