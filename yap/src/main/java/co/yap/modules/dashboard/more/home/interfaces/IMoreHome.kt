package co.yap.modules.dashboard.more.home.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IMoreHome {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
     }

    interface View : IBase.View<ViewModel>
}