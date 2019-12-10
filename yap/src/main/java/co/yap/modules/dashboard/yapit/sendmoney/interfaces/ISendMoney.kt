package co.yap.modules.dashboard.yapit.sendmoney.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleLiveEvent

class ISendMoney {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        val backButtonPressEvent: SingleLiveEvent<Boolean>
    }

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
        var leftButtonVisibility:Int
        var enableAddBeneficiary: Boolean
    }
}