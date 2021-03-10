package co.yap.modules.dashboard.store.cardplans.interfaces

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.store.cardplans.CardPlans
import co.yap.yapcore.IBase

interface IMainCardPlans {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
        fun removeObservers()
    }

    interface ViewModel : IBase.ViewModel<State>{
        var cards : MutableList<CardPlans>
        val cardTag: String get() = "CARD-TAG"
    }

    interface State : IBase.State
}