package co.yap.modules.dashboard.more.yapforyou.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYAPForYou {
    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var currentAchievement: ObservableField<Y4YAchievementData>
        var isNoCompletedAchievements: ObservableBoolean

    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adaptor: YAPForYouAdapter
        fun handlePressOnView(id: Int)
        fun setAchievements(achievementsResponse: ArrayList<Achievement>)
        fun setSelectedAchievement(y4YAchievementData: Y4YAchievementData)
    }

    interface View : IBase.View<ViewModel> {
        fun addObservers()
    }
}
