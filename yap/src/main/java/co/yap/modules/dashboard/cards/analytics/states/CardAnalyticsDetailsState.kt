package co.yap.modules.dashboard.cards.analytics.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalyticsDetails
import co.yap.yapcore.BaseState

class CardAnalyticsDetailsState : BaseState(), ICardAnalyticsDetails.State {
    override var title: ObservableField<String> = ObservableField("Title")
    override var totalSpendings: ObservableField<String> = ObservableField("Spendings")
    override var transactionCount: ObservableField<String> = ObservableField("Count")
    override var date: ObservableField<String> = ObservableField("Date")
    override var ImageUrl: ObservableField<String> = ObservableField("Url")
}