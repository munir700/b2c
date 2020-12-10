package co.yap.modules.dashboard.more.yapforyou.interfaces

import androidx.databinding.ObservableField
import co.yap.databinding.FragmentAchievementBinding
import co.yap.modules.dashboard.more.yapforyou.adapters.AchievementTaskAdaptor
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAchievement {

    interface View : IBase.View<ViewModel> {
        fun getBinding(): FragmentAchievementBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val adapter: AchievementTaskAdaptor
        fun handlePressOnButton(id: Int)
    }

    interface State : IBase.State {
        var achievementIcon: ObservableField<Int>
    }
}
