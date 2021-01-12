package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.databinding.FragmentCompletedAchievementsBinding
import co.yap.modules.dashboard.more.yapforyou.adapters.CompletedAchievementsAdaptor
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICompletedAchievements {

    interface View : IBase.View<ViewModel> {
        fun getBinding(): FragmentCompletedAchievementsBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val adapter: CompletedAchievementsAdaptor
        fun handlePressOnButton(id: Int)
        fun setSelectedAchievement(y4YAchievementData: Y4YAchievementData)
    }

    interface State : IBase.State
}