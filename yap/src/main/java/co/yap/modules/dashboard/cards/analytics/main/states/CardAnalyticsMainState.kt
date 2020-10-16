package co.yap.modules.dashboard.cards.analytics.main.states

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.yapcore.BaseState


class CardAnalyticsMainState : BaseState(), ICardAnalyticsMain.State {
    override var toolbarVisibility: ObservableBoolean = ObservableBoolean()
    override var leftButtonVisibility:  ObservableBoolean = ObservableBoolean()
}