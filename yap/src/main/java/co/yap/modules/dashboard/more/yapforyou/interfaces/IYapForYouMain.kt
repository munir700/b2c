package co.yap.modules.dashboard.more.yapforyou.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.more.yapforyou.YapForYouManager
import co.yap.modules.dashboard.more.yapforyou.models.YapForYouDataModel
import co.yap.networking.transactions.responsedtos.achievement.Achievement
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYapForYouMain {

    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var achievement: Achievement?
        var selectedPosition: Int
        var achievements: MutableList<Achievement>
        fun handlePressButton(id: Int)
        fun getDescriptionContent(tag: String)
        fun configureYFYManager(tag : String)
    }

    interface State : IBase.State {
        var toolbarVisibility: ObservableBoolean
        var leftIcon: ObservableField<Int>
        var leftIconVisibility: ObservableBoolean
        var descriptionDataModel: ObservableField<YapForYouDataModel>?
        var yapForYouManager: YapForYouManager
        var yfyFeatureTitle : ObservableField<String>
    }
}