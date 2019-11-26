package co.yap.modules.dashboard.cards.analytics.interfaces

import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardAnalytics {
    interface View : IBase.View<ViewModel> {
        fun setObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var selectedModel: MutableLiveData<AnalyticsItem>
        var parentViewModel: ICardAnalyticsMain.ViewModel
        fun fetchCardAnalytics()
        fun handlePressOnView(id: Int)

    }

    interface State : IBase.State {
        var monthlyAverageString: String
        var currencyType: String
        var selectedItemSpentValue: String
        var selectedItemPercentage: String
        var selectedItemName: String
        var selectedItemPosition: Int
    }
}