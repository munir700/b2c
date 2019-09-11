package co.yap.modules.dashboard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapStore {

    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handleClickIOnView(id: Int)
    }

    interface View : IBase.View<ViewModel>
}