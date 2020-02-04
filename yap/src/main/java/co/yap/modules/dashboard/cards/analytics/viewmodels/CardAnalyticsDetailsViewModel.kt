package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.adaptors.CardAnalyticsDetailsAdapter
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalyticsDetails
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsDetailsState
import co.yap.networking.models.ApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class CardAnalyticsDetailsViewModel(application: Application) :
    BaseViewModel<ICardAnalyticsDetails.State>(application), ICardAnalyticsDetails.ViewModel {
    override val state = CardAnalyticsDetailsState()
    override val adapter: ObservableField<CardAnalyticsDetailsAdapter> = ObservableField<CardAnalyticsDetailsAdapter>()
    override var clickEvent: SingleClickEvent? = SingleClickEvent()

    override fun handleOnClickEvent(id: Int) {
        clickEvent?.setValue(id)
    }
    override fun onCreate() {
        super.onCreate()
        getCardAnalyticsDetails()

    }

    override fun getCardAnalyticsDetails() {
        adapter.get()?.setData(MutableList(10){ ApiResponse() })
    }
}