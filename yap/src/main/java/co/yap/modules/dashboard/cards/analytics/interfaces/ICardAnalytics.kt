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
        fun fetchCardCategoryAnalytics()
        fun fetchCardMerchantAnalytics()
        fun handlePressOnView(id: Int)

    }

    interface State : IBase.State {
        var monthlyAverageString: String
        var monthlyMerchantAverageString: String
        var currencyType: String?
        var monthlyCategoryAvgAmount: String?
        var monthlyMerchantAvgAmount: String?
        var selectedItemSpentValue: String
        var selectedItemPercentage: String
        var selectedItemName: String?
        var selectedItemPosition: Int
        var totalSpent: String?
        var totalCategorySpent: String?
        var totalMerchantSpent: String?
    }
}