package co.yap.modules.dashboard.cards.addpaymentcard.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

interface IAddPaymentCard {

    interface State : IBase.State{
        var tootlBarTitle: String
        var tootlBarVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var physicalCardFee: String
        var virtualCardFee: String
        fun getVirtualCardFee()
        fun getPhysicalCardFee()
    }

    interface View : IBase.View<ViewModel>
}