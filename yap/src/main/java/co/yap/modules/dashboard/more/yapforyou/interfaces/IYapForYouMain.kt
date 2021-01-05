package co.yap.modules.dashboard.more.yapforyou.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YAPForYouGoal
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapForYouMain {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var selectedAchievement: Y4YAchievementData?
        var selectedAchievementTask: YAPForYouGoal?
        var selectedPosition: Int
        var achievementsList: MutableList<Y4YAchievementData>
        fun handlePressButton(id: Int)
    }

    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var leftIcon: ObservableField<Int>
        var leftIconVisibility: ObservableBoolean
        var descriptionDataModel: ObservableField<YapForYouDataModel>?
    }
}
