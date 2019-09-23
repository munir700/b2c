package co.yap.modules.dashboard.cards.reportcard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IRepostOrStolenCard {
    interface State : IBase.State {

        var cardType: String
        var maskedCardNumber: String

    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        val clickEvent: SingleClickEvent
        val HOT_LIST_REASON: Int
        val CARD_REORDER_SUCCESS: Int
        val cardFee: String

        fun handlePressOnBackButton()
        fun handlePressOnDamagedCard(id: Int)
        fun handlePressOnLostOrStolen(id: Int)
        fun handlePressOnReportAndBlockButton(id: Int)

        fun requestConfirmBlockCard()
    }

    interface View : IBase.View<ViewModel>
}