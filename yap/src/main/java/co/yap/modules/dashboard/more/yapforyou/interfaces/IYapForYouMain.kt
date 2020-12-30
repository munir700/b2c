package co.yap.modules.dashboard.more.yapforyou.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.YapForYouManager
import co.yap.modules.dashboard.more.yapforyou.models.Y4YAchievementData
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapForYouMain {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var achievement: Y4YAchievementData?
        var selectedPosition: Int
        var achievements: MutableList<Y4YAchievementData>
        fun handlePressButton(id: Int)
        fun getDescriptionContent(tag: String): YapForYouDataModel?
        fun configureYFYManager(tag : String)
        fun getYfyTag() : String
    }

    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var leftIcon: ObservableField<Int>
        var leftIconVisibility: ObservableBoolean
        var descriptionDataModel: ObservableField<YapForYouDataModel>?
        var yapForYouManager: YapForYouManager
    }
}