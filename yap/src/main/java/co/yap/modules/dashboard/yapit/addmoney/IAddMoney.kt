package co.yap.modules.dashboard.yapit.addmoney

import co.yap.databinding.FragmentAddMoneyBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddMoney {
    interface View : IBase.View<ViewModel> {
        fun getBinding(): FragmentAddMoneyBinding
        fun setObservers()
        fun removeObservers()
    }
    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun getAddMoneyOptions(): MutableList<AddMoneyOptions>
    }
    interface State : IBase.State {}
}