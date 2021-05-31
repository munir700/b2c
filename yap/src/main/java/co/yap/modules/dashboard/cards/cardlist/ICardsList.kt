package co.yap.modules.dashboard.cards.cardlist

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardsList {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
    }

    interface State : IBase.State
}