package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import androidx.databinding.ObservableField
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalyticsDetails
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsDetailsState
import co.yap.modules.dashboard.home.adaptor.TransactionsListingAdapter
import co.yap.networking.transactions.responsedtos.transaction.Transaction
import co.yap.yapcore.SingleClickEvent

class CardAnalyticsDetailsViewModel(application: Application) :
    CardAnalyticsBaseViewModel<ICardAnalyticsDetails.State>(application),
    ICardAnalyticsDetails.ViewModel {
    override val state = CardAnalyticsDetailsState()
    override val adapter: ObservableField<TransactionsListingAdapter> =
        ObservableField<TransactionsListingAdapter>()
    override var clickEvent: SingleClickEvent? = SingleClickEvent()

    override fun handleOnClickEvent(id: Int) {
        clickEvent?.setValue(id)
    }

    override fun onCreate() {
        super.onCreate()
        getCardAnalyticsDetails()
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(state.title.get() ?: "Analytics")
    }

    override fun getCardAnalyticsDetails() {
        adapter.get()?.setList(getMutableList())
    }

    private fun getMutableList(): List<Transaction> {
        var list: MutableList<Transaction> = ArrayList<Transaction>()
        list.add(
            Transaction(
                title = "Something",
                currency = "AED",
                updatedDate = "2010-09-22T07:28:56",
                creationDate = "2009-04-22T07:28:19"
            )
        )
        list.add(
            Transaction(
                title = "Something1",
                currency = "AED",
                updatedDate = "2010-09-22T07:28:56",
                creationDate = "2009-04-22T07:28:19"
            )
        )
        list.add(
            Transaction(
                title = "Something2",
                currency = "AED",
                updatedDate = "2010-09-22T07:28:56",
                creationDate = "2009-04-22T07:28:19"
            )
        )
        list.add(
            Transaction(
                title = "Something3",
                currency = "AED",
                updatedDate = "2010-09-22T07:28:56",
                creationDate = "2009-04-22T07:28:19"
            )
        )
        list.add(
            Transaction(
                title = "Something4",
                currency = "AED",
                updatedDate = "2010-09-22T07:28:56",
                creationDate = "2009-04-22T07:28:19"
            )
        )
        return list
    }
}