package co.yap.modules.dashboard.cards.reportcard.interfaces

import co.yap.yapcore.IBase
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
        fun handlePressOnBackButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>
}