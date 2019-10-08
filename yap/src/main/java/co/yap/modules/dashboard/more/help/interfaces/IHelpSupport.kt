package co.yap.modules.dashboard.more.help.interfaces

import androidx.databinding.ObservableField
import androidx.databinding.ViewDataBinding
import co.yap.databinding.FragmentMoreHomeBinding
import co.yap.modules.dashboard.more.home.models.MoreOption
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IHelpSupport {
    interface State : IBase.State{
        var title: ObservableField<String>
        var contactPhone: ObservableField<String>
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getHelpDeskPhone()
    }

    interface View : IBase.View<ViewModel>{
        fun getBinding(): ViewDataBinding
    }
}