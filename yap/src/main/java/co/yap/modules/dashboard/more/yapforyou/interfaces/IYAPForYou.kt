package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYAPForYou {

    interface State : IBase.State {
//        var fullName: String


    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnBackButton()
        fun handlePressOnBadge(id: Int)

    }

    interface View : IBase.View<ViewModel>

}