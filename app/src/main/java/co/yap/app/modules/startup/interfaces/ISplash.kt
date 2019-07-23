package co.yap.app.modules.startup.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface ISplash {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val splashComplete: SingleLiveEvent<Boolean>
    }

    interface State : IBase.State
}