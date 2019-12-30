package co.yap.modules.dashboard.cards.reordercard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IReorderCard {

    interface State : IBase.State {
        var cardType: String
        var valid: Boolean
        var cardFee: String
    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleClickEvent
        val clickEvent: SingleClickEvent
        fun handlePressOnBackButton()
    }

    interface View : IBase.View<ViewModel>
}