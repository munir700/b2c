package co.yap.modules.dashboard.cards.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ISpareCards  {

    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
    }

    interface View : IBase.View<ViewModel>
}