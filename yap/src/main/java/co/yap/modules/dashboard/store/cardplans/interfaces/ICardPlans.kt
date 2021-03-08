package co.yap.modules.dashboard.store.cardplans.interfaces

import co.yap.databinding.FragmentCardPlansBinding
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.modules.dashboard.store.cardplans.CardPlansAdapter
import co.yap.yapcore.IBase

interface ICardPlans {
    interface View : IBase.View<ViewModel>{
        fun getBindings(): FragmentCardPlansBinding
        fun navigateToFragment(data: CardPlans, actionId: Int)
    }
    interface ViewModel : IBase.ViewModel<State>{
        var cardAdapter : CardPlansAdapter
    }
    interface State : IBase.State
}