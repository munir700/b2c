package co.yap.modules.dashboard.cards.reportcard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IRepostOrStolenCard {
    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int

        var cardType: String
        var maskedCardNumber: String
//        var report: String


    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        val clickEvent: SingleClickEvent

        fun handlePressOnBackButton()
        fun handlePressOnDamagedCard(id: Int)
        fun handlePressOnLostOrStolen(id: Int)
        fun handlePressOnReportAndBlockButton(id: Int)
    }

    interface View : IBase.View<ViewModel>
}