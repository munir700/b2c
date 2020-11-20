package co.yap.modules.dashboard.yapit.addmoney.landing

import co.yap.databinding.FragmentAddMoneyLandingBinding
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IAddMoneyLanding {
    interface View : IBase.View<ViewModel> {
        fun getBinding(): FragmentAddMoneyLandingBinding
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val landingAdapter: AddMoneyLandingAdapter
        fun handlePressOnView(id: Int)
        val clickEvent: SingleClickEvent
        fun getAddMoneyOptions(): MutableList<AddMoneyLandingOptions>
    }

    interface State : IBase.State {}
}