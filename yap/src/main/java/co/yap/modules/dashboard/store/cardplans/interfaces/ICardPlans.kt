package co.yap.modules.dashboard.store.cardplans.interfaces

import androidx.constraintlayout.widget.ConstraintLayout
import co.yap.databinding.FragmentCardPlansBinding
import co.yap.modules.dashboard.store.cardplans.adaptors.CardPlansAdapter
import co.yap.yapcore.IBase

interface ICardPlans {
    interface View : IBase.View<ViewModel>{
        fun getBindings(): FragmentCardPlansBinding
        fun navigateToFragment(data: String)
    }
    interface ViewModel : IBase.ViewModel<State>{
        var cardAdapter : CardPlansAdapter
    }
    interface State : IBase.State
}