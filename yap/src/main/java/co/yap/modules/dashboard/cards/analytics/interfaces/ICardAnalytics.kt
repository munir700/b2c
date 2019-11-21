package co.yap.modules.dashboard.cards.analytics.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.yapcore.IBase

interface ICardAnalytics {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        fun fetchCardAnalytics()
        var selectedModel: MutableLiveData<AnalyticsItem>
        var parentViewModel: ICardAnalyticsMain.ViewModel

    }

    interface State : IBase.State
}