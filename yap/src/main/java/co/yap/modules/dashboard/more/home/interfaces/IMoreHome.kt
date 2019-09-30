package co.yap.modules.dashboard.more.home.interfaces

import co.yap.databinding.FragmentMoreHomeBinding
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IMoreHome {
    interface State : IBase.State

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getMoreOptions(): MutableList<MoreOption>
    }

    interface View : IBase.View<ViewModel>{
        fun getBinding(): FragmentMoreHomeBinding
    }
}