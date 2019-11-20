package co.yap.modules.dashboard.cards.analytics.main.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.states.CardAnalyticsMainState
import co.yap.yapcore.SingleClickEvent

class CardAnalyticsMainViewModel(application: Application) :
    CardAnalyticsBaseViewModel<ICardAnalyticsMain.State>(application),
    ICardAnalyticsMain.ViewModel {
    override val state: CardAnalyticsMainState =
        CardAnalyticsMainState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }
}