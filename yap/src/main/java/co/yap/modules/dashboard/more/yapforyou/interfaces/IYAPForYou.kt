package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.modules.dashboard.more.yapforyou.Achievement
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYAPForYou {

    interface State : IBase.State {
        var selectedAchievementTitle: String
        var selectedAchievementPercentage: String?
        var selectedAchievementImage: Int?

    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getAchievements(): MutableList<Achievement>
    }

    interface View : IBase.View<ViewModel>

}