package co.yap.modules.dashboard.cards.reportcard.interfaces

import co.yap.networking.cards.responsedtos.Card
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IReportStolenActivity {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var card: Card?
    }

    interface View : IBase.View<ViewModel>
}