package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalytics
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsState
import co.yap.yapcore.SingleClickEvent

class CardAnalyticsViewModel(application: Application) :
    CardAnalyticsBaseViewModel<ICardAnalytics.State>(application = application),
    ICardAnalytics.ViewModel {
    override val state: CardAnalyticsState = CardAnalyticsState()
    override var selectedModel: MutableLiveData<AnalyticsItem> = MutableLiveData()
    override lateinit var parentViewModel: ICardAnalyticsMain.ViewModel
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun onCreate() {
        super.onCreate()
        parentVM?.let {
            parentViewModel = it
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Analytics")

    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun fetchCardAnalytics() {
        val list = ArrayList<AnalyticsItem>()
        val list2 = ArrayList<AnalyticsItem>()
        for (i in 0..4)
            list.add(AnalyticsItem("Shopping", "4 transactions", "AED 600", "42%"))

        for (i in 0..4)
            list2.add(AnalyticsItem("Amazon", "3 transactions", "AED 1450", "52%"))

        parentVM?.categoryAnalyticsItemLiveData?.value = list
        parentVM?.merchantAnalyticsItemLiveData?.value = list2

    }
}