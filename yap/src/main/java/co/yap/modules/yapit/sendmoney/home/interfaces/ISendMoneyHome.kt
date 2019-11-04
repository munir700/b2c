package co.yap.modules.yapit.sendmoney.home.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface ISendMoneyHome {
    interface State : IBase.State {
    }

    interface ViewModel : IBase.ViewModel<State> {
        val backButtonPressEvent: SingleLiveEvent<Boolean>
        var clickEvent: SingleClickEvent
        fun handlePressOnBackButton()
        fun handlePressOnAddNow(id: Int)
    }

    interface View : IBase.View<ViewModel>
}