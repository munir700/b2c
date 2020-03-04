package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
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
        var adaptor: YAPForYouAdapter
        fun handlePressOnView(id: Int)
        fun getAchievements()
        fun getAchievementIcon(position: Int,isWithBadged:Boolean = false): Int
    }

    interface View : IBase.View<ViewModel>

}