package co.yap.modules.dashboard.cards.paymentcarddetail.interfaces

import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.networking.cards.responsedtos.Card
import co.yap.networking.cards.responsedtos.CardDetail
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface IPaymentCardDetail {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val EVENT_FREEZE_UNFREEZE_CARD: Int get() = 1
        val EVENT_CARD_DETAILS: Int get() = 2
        val clickEvent: SingleClickEvent
        var card: Card
        var cardDetail : CardDetail
        val transactionLogicHelper: TransactionLogicHelper
        fun handlePressOnView(id: Int)
        fun getCardBalance()
        fun freezeUnfreezeCard()
        fun getCardDetails()
        fun removeCard()
    }

    interface State : IBase.State {
        var accountType: String
        var cardType: String
        var cardPanNumber: String
        var cardBalance: String
        var cardName: String
        var blocked: Boolean
        var physical: Boolean
    }
}