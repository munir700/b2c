package co.yap.modules.dashboard.sendmoney.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

class ISendMoney {
    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>
}