package co.yap.modules.dashboard.more.yapforyou.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapForYouMain {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var selectedAchievement: ObservableField<Y4YAchievementData?>
        var selectedAchievementGoal: ObservableField<YAPForYouGoal?>
        var achievementsList: MutableList<Y4YAchievementData>
        var achievementsResponse: MutableLiveData<ArrayList<Achievement>>
        fun handlePressButton(id: Int)
        fun getAchievements()
        fun getMockApiResponse()
    }

    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var leftIcon: ObservableField<Int>
        var leftIconVisibility: ObservableBoolean
    }
}
