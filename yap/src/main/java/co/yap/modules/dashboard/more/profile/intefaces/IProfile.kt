package co.yap.modules.dashboard.more.profile.intefaces

import co.yap.yapcore.IBase

interface IProfile {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
    }

    interface View : IBase.View<ViewModel>
}