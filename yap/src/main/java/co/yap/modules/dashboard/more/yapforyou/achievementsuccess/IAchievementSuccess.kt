package co.yap.modules.dashboard.more.yapforyou.achievementsuccess

import co.yap.databinding.FragmentAchievementSuccessBinding
import co.yap.yapcore.IBase

interface IAchievementSuccess {
    interface View : IBase.View<ViewModel> {
        fun getBinding(): FragmentAchievementSuccessBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
    }

    interface State : IBase.State {
    }
}