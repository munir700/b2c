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
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.managers.MyUserManager

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

    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle("Analytics")

    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun fetchCardCategoryAnalytics() {
        //   val categoryList = ArrayList<TxnAnalytic>()

        //call api here
        launch {
            state.loading = true
            when (val response = repository.getAnalyticsByCategoryName(
                MyUserManager.getCardSerialNumber(),
                DateUtils.getCurrentDate()
            )) {
                is RetroApiResponse.Success -> {
                    parentVM?.categoryAnalyticsItemLiveData?.value = response.data.data.txnAnalytics
                    state.monthlyAvgAmount = response.data.data.monthlyAvgAmount?.toString()
                    state.setUpString(state.currencyType, response.data.data.monthlyAvgAmount.toString())
                   // clickEvent.postValue(Constants.CATEGORY_AVERAGE_AMOUNT_VALUE)
                    fetchCardMerchantAnalytics()
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }

//        parentVM?.categoryAnalyticsItemLiveData?.value = categoryList

        /*list2.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                0.07663441603317209,
                24
            )
        )*/


    }

    override fun fetchCardMerchantAnalytics() {
        //val merchantList = ArrayList<TxnAnalytic>()
//        launch {
//            state.loading = true
//            when (val response = repository.getAnalyticsByMerchantName(
//                MyUserManager.getCardSerialNumber(),
//                DateUtils.getCurrentDate()
//            )) {
//                is RetroApiResponse.Success -> {
//                    parentVM?.merchantAnalyticsItemLiveData?.value = response.data.txnAnalytics
//                }
//                is RetroApiResponse.Error -> state.toast = response.error.message
//            }
//            state.loading = false
//        }
        //   parentVM?.merchantAnalyticsItemLiveData?.value = merchantList

    }
}