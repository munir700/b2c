package co.yap.modules.dashboard.cards.addpaymentcard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IAddPaymentCard {

    interface State : IBase.State{
        var tootlBarTitle: String
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>
}