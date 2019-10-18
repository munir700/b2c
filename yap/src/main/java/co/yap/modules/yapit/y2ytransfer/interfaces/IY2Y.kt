package co.yap.modules.yapit.y2ytransfer.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IY2Y {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton()
        fun handlePressOnTickButton()
        fun handlePressOnView(id : Int)
        val backButtonPressEvent: SingleLiveEvent<Boolean>
//        val clickEvent: SingleClickEvent

    }

    interface View : IBase.View<ViewModel>
}