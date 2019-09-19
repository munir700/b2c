package co.yap.modules.dashboard.cards.paymentcarddetail.interfaces

import co.yap.modules.dashboard.helpers.transaction.TransactionLogicHelper
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface IPaymentCardDetail {

    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        val transactionLogicHelper: TransactionLogicHelper
        fun handlePressOnView(id: Int)
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