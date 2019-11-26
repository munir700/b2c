package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalytics
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.TxnAnalytic
import co.yap.yapcore.SingleClickEvent

class CardAnalyticsViewModel(application: Application) :
    CardAnalyticsBaseViewModel<ICardAnalytics.State>(application = application),
    ICardAnalytics.ViewModel {
    override val state: CardAnalyticsState = CardAnalyticsState(application)
    override var selectedModel: MutableLiveData<AnalyticsItem> = MutableLiveData()
    val repository: TransactionsRepository = TransactionsRepository
    override lateinit var parentViewModel: ICardAnalyticsMain.ViewModel
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override fun onCreate() {
        super.onCreate()
        parentVM?.let {
            parentViewModel = it
        }
        state.currencyType = "AED"
        state.setUpString(state.currencyType, "5,600.00")
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Analytics")

    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun fetchCardCategoryAnalytics() {
        val categoryList = ArrayList<TxnAnalytic>()

        //call api here
        launch {
            state.loading = true
            when (val response = repository.getAnalyticsByCategoryName("826336237263632", "12-12-2019")) {
                is RetroApiResponse.Success -> {

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }

        parentVM?.categoryAnalyticsItemLiveData?.value = categoryList

        /*list2.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                0.07663441603317209,
                24
            )
        )
        list2.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Uber",
                "887.12",
                0.07663441603317209,
                24
            )
        )
        list2.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/emirates.jpg",
                "Emirates",
                "887.12",
                0.07663441603317209,
                24
            )
        )
        list2.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/emirates.jpg",
                "CANDY",
                "887.12",
                0.07663441603317209,
                24
            )
        )
        list2.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/emirates.jpg",
                "Others",
                "887.12",
                0.07663441603317209,
                24
            )
        )

        list.add(
            TxnAnalytic(
                null,
                "Food and drink",
                "887.12",
                0.07663441603317209,
                10
            )
        )
        list.add(
            TxnAnalytic(
                null,
                "Travel",
                "887.12",
                0.07663441603317209,
                20
            )
        )
        list.add(
            TxnAnalytic(
                null,
                "Shopping",
                "887.12",
                0.07663441603317209,
                12
            )
        )
        list.add(
            TxnAnalytic(
                null,
                "Health and beauty",
                "887.12",
                0.07663441603317209,
                4
            )
        )
        list.add(
            TxnAnalytic(
                null,
                "Others",
                "887.12",
                0.07663441603317209,
                2
            )
        )
//            list.add(AnalyticsItem("Shopping", 12, "5687.16", 0.5145))
//
//        for (i in 0..4)
//            list2.add(AnalyticsItem("Amazon", 4, "5687.16", 0.51234))*/


    }

    override fun fetchCardMerchantAnalytics() {
        val merchantList = ArrayList<TxnAnalytic>()
        launch {
            state.loading = true
            when (val response = repository.getAnalyticsByCategoryName("826336237263632", "12-12-2019")) {
                is RetroApiResponse.Success -> {

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
        parentVM?.merchantAnalyticsItemLiveData?.value = merchantList

    }
}