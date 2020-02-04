package co.yap.modules.dashboard.cards.analytics.interfaces

import co.yap.yapcore.IBase

interface ICardAnalyticsDetails {
    interface View : IBase.View<ViewModel> {

    }
    interface ViewModel : IBase.ViewModel<State> {

    }
    interface State : IBase.State {

    }
}