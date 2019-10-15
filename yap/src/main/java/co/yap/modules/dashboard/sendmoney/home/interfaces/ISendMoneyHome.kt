package co.yap.modules.dashboard.sendmoney.home.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

class ISendMoneyHome {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent
        fun handlePressOnBackButton()
        fun handlePressOnAddNow()
    }

    interface View : IBase.View<ViewModel>
}