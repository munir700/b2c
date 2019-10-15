package co.yap.modules.dashboard.sendmoney.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

class ISendMoney {
    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
        var enableAddCard: Boolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnAddButton(id:Int)
        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface View : IBase.View<ViewModel>
}