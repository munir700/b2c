package co.yap.modules.dashboard.store.young.landing.benefits

import co.yap.modules.dashboard.store.young.landing.benefits.adapter.YoungBenefitsAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IYoungBenefits {
    interface State : IBase.State
    interface ViewModel : IBase.ViewModel<State>{
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        val benefitsAdapter : YoungBenefitsAdapter
    }
    interface View : IBase.View<ViewModel>
}