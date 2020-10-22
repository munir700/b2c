package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalyticsDetails
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsDetailsState
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.managers.SessionManager
import java.util.*
import kotlin.collections.ArrayList

class CardAnalyticsDetailsViewModel(application: Application) :
    CardAnalyticsBaseViewModel<ICardAnalyticsDetails.State>(application),
    ICardAnalyticsDetails.ViewModel {
    override val state = CardAnalyticsDetailsState()
    override val adapter: ObservableField<TransactionsListingAdapter> =
        ObservableField<TransactionsListingAdapter>()
    val repository: TransactionsRepository = TransactionsRepository
    var currentCalendar: Calendar = Calendar.getInstance()
    var list: MutableList<Transaction>? = ArrayList<Transaction>()

    override var clickEvent: SingleClickEvent? = SingleClickEvent()

    override fun handleOnClickEvent(id: Int) {
        clickEvent?.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        fetchMerchantTransactions(
            Constants.MERCHANT_TYPE,
            DateUtils.dateToString(currentCalendar.time, "yyyy-MM-dd")
        )
        getCardAnalyticsDetails()
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(state.title.get()?.trim() ?: "Analytics")
    }

    override fun getCardAnalyticsDetails() {
        list?.let { adapter.get()?.setList(it) }
    }

    override fun fetchMerchantTransactions(merchantType: String, currentDate: String) {
        launch {
            state.loading = true
            when (val response = repository.getTransactionsOfMerchant(
                merchantType,
                SessionManager.getCardSerialNumber(),
                currentDate, state.title.get() ?: ""
            )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        if (!it.txnAnalytics.isNullOrEmpty()){
                            list = it.txnAnalytics
                        }
                        state.loading = false
                    }
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
            }
        }
    }
}