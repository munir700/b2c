package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.databinding.FragmentCompletedAchievementsBinding
import co.yap.modules.dashboard.more.yapforyou.adapters.YAPForYouAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICompletedAchievements {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun getBinding(): FragmentCompletedAchievementsBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        val adapter: YAPForYouAdapter
        fun handlePressOnButton(id: Int)
    }

    interface State : IBase.State
}