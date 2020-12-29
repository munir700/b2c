package co.yap.modules.dashboard.more.yapforyou.interfaces

import androidx.databinding.ObservableBoolean
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYAPForYou {
    interface State : IBase.State {
        var selectedAchievementTitle: String
        var selectedAchievementPercentage: String?
        var selectedAchievementImage: Int?
        var toolbarVisibility: ObservableBoolean
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        var adaptor: YAPForYouAdapter
        fun handlePressOnView(id: Int)
        fun getAchievements()
    }

    interface View : IBase.View<ViewModel> {
        fun addObservers()
    }

}