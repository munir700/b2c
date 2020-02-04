package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalyticsDetails
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsDetailsState
import co.yap.yapcore.BaseViewModel

class CardAnalyticsDetailsViewModel(application: Application) :
    BaseViewModel<ICardAnalyticsDetails.State>(application), ICardAnalyticsDetails.ViewModel {
    override val state = CardAnalyticsDetailsState()
}