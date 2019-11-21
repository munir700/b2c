package co.yap.modules.dashboard.cards.analytics.interfaces

import co.yap.yapcore.IBase

interface ICardAnalytics {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State>{
        fun fetchCardAnalytics()
    }
    interface State : IBase.State
}