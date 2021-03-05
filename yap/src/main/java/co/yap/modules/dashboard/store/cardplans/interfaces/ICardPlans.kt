package co.yap.modules.dashboard.store.cardplans.interfaces

import co.yap.modules.dashboard.store.cardplans.CardPlansAdapter
import co.yap.yapcore.IBase

interface ICardPlans {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        var cardAdapter : CardPlansAdapter
    }
    interface State : IBase.State
}