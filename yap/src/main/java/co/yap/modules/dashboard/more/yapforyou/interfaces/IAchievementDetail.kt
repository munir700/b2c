package co.yap.modules.dashboard.more.yapforyou.interfaces

import co.yap.databinding.FragmentAchievementDetailBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent


interface IAchievementDetail {

    interface View : IBase.View<ViewModel> {
        fun addObservers()
        fun getBinding(): FragmentAchievementDetailBinding
    }

    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent
        fun handlePressOnButton(id: Int)
    }

    interface State : IBase.State
}