package co.yap.modules.dashboard.cards.analytics.main.states

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.yapcore.BaseState


class CardAnalyticsMainState : BaseState(), ICardAnalyticsMain.State {
    override var toolBarTitle: ObservableField<String> = ObservableField("title")
    override var toolBarVisibility: ObservableField<Boolean> = ObservableField(true)
    override var leftButtonVisibility: ObservableField<Boolean> = ObservableField(true)
}