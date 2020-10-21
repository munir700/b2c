package co.yap.modules.dashboard.cards.analytics.interfaces

import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.yapcore.IBase

interface IMerchantAnalytics {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }
    interface ViewModel : IBase.ViewModel<State>{
        var pViewModel: ICardAnalyticsMain.ViewModel
    }
    interface State : IBase.State
}