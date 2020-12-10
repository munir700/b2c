package co.yap.modules.dashboard.more.yapforyou.achievementdetail

import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAchievementDetail {
    interface View : IBase.View<ViewModel> {
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnButton(id: Int)
    }

    interface State : IBase.State {
    }
}
