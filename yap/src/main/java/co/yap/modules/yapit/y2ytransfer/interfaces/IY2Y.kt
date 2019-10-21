package co.yap.modules.yapit.y2ytransfer.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent

interface IY2Y {

    interface State : IBase.State {
        var tootlBarTitle: String
        var tootlBarVisibility: Int
        var rightButtonVisibility:Int
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun handlePressOnBackButton(id : Int)
         fun handlePressOnView(id : Int)
         val clickEvent: SingleClickEvent

    }

    interface View : IBase.View<ViewModel>
}