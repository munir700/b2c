package co.yap.modules.dashboard.store.cardplans.interfaces

import androidx.databinding.ObservableField
import co.yap.databinding.FragmentPrimeMetalCardBinding
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.adaptors.PlanBenefitsAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IPrimeMetalCard {
    interface View : IBase.View<ViewModel> {
        fun getBindings(): FragmentPrimeMetalCardBinding
        fun initVideoView()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun getCardPlan(tag: String): CardPlans?
        fun getCardBenefits(tag: String): MutableList<String>
        var planBenefitsAdapter : PlanBenefitsAdapter
        var clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        val cardPlans: ObservableField<CardPlans>
        val planToView : ObservableField<String>
    }
}