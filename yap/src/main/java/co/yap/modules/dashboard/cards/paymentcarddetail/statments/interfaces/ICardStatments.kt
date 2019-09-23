package co.yap.modules.dashboard.cards.paymentcarddetail.statments.interfaces

import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface ICardStatments {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        var card : Card
    }

    interface State : IBase.State {
        var message: String
    }
}