package co.yap.modules.dashboard.cards.analytics.interfaces

import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsMainViewModel
import co.yap.yapcore.IBase

interface ICategoryAnalytics {
    interface View : IBase.View<ViewModel>
    interface ViewModel : IBase.ViewModel<State> {
        var pViewModel: ICardAnalyticsMain.ViewModel
    }

    interface State : IBase.State
}