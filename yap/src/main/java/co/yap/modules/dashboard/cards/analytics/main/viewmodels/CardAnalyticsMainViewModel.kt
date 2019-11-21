package co.yap.modules.dashboard.cards.analytics.main.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.states.CardAnalyticsMainState
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CardAnalyticsMainViewModel(application: Application) :
    BaseViewModel<ICardAnalyticsMain.State>(application),
    ICardAnalyticsMain.ViewModel {
    override val state: CardAnalyticsMainState =
        CardAnalyticsMainState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val categoryAnalyticsItemLiveData: MutableLiveData<ArrayList<AnalyticsItem>> =
        MutableLiveData()
    override val merchantAnalyticsItemLiveData: MutableLiveData<ArrayList<AnalyticsItem>> =
        MutableLiveData()

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

}