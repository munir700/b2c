package co.yap.modules.dashboard.cards.analytics.interfaces

import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.adaptors.CardAnalyticsDetailsAdapter
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface ICardAnalyticsDetails {
    interface View : IBase.View<ViewModel> {

    }
    interface ViewModel : IBase.ViewModel<State> {
        var clickEvent: SingleClickEvent?
        fun handleOnClickEvent(id: Int)
        val adapter: ObservableField<CardAnalyticsDetailsAdapter>
        fun getCardAnalyticsDetails()

    }
    interface State : IBase.State {

    }
}