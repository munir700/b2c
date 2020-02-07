package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.modules.dashboard.more.yapforyou.Achievements
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYAPForYou {

    interface State : IBase.State {
        var selectedAchievementTitle:String
        var selectedAchievementPercentage:String?

    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getAchievements(): MutableList<Achievements>
    }

    interface View : IBase.View<ViewModel>

}