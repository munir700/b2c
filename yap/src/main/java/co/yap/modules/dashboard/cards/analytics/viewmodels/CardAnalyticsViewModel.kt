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
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.managers.MyUserManager
import java.text.SimpleDateFormat
import java.util.*

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
        MyUserManager.user?.creationDate?.let {
            val date =
                DateUtils.stringToDate(
                    it,
                    DateUtils.FORMAT_LONG_INPUT
                )

            date?.let {
                if (Date().month == date.month) {
                    state.nextMonth = false
                    state.previousMonth = false
                } else {
                    if (Date().month > date.month) {
                        state.previousMonth = true
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setToolBarTitle(getString(Strings.screen_card_analytics_tool_bar_title))
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun fetchCardCategoryAnalytics() {
//           val categoryList = ArrayList<TxnAnalytic>()

        //call api here
        launch {
            state.loading = true
            when (val response = repository.getAnalyticsByCategoryName(
                MyUserManager.getCardSerialNumber(),
                DateUtils.getCurrentDate()
            )) {
                is RetroApiResponse.Success -> {
                    parentVM?.categoryAnalyticsItemLiveData?.value = response.data.data.txnAnalytics
                    state.monthlyCategoryAvgAmount = response.data.data.monthlyAvgAmount?.toString()
                    state.setUpString(
                        state.currencyType,
                        Utils.getFormattedCurrency(state.monthlyCategoryAvgAmount)
                    )
                    state.totalCategorySpent =
                        state.currencyType + " ${Utils.getFormattedCurrency(response.data.data.totalTxnAmount.toString())}"
                    state.totalSpent = state.totalCategorySpent
                    clickEvent.postValue(Constants.CATEGORY_AVERAGE_AMOUNT_VALUE)
                    fetchCardMerchantAnalytics()
                    state.selectedMonth = DateUtils.convertAnalyticsDate(response.data.data.date)

                    val parser = SimpleDateFormat("yyyy-MM")
                    parser.timeZone = TimeZone.getTimeZone("UTC")
                    val strDate = parser.parse(response.data.data.date)

                    if (System.currentTimeMillis() > strDate.time) {
                        state.nextMonth = true
//                        if (datePassed) {
//
//                        }
                    } else {

                    }

                    2019 - 11
                    //"date" : "2019-10",
//November , 2019
                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }
        /*categoryList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        categoryList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        categoryList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        categoryList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        categoryList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )


        parentVM?.categoryAnalyticsItemLiveData?.value = categoryList*/

        /*list2.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )*/


    }

    override fun fetchCardMerchantAnalytics() {
//        val merchantList = ArrayList<TxnAnalytic>()
        launch {
            state.loading = true
            when (val response = repository.getAnalyticsByMerchantName(
                MyUserManager.getCardSerialNumber(),
                DateUtils.getCurrentDate()
            )) {
                is RetroApiResponse.Success -> {
                    parentVM?.merchantAnalyticsItemLiveData?.value = response.data.data.txnAnalytics
                    state.monthlyMerchantAvgAmount = response.data.data.monthlyAvgAmount?.toString()
//                    state.monthlyMerchantAvgAmount = "2000.0"
                    state.totalMerchantSpent =
                        state.currencyType + " ${Utils.getFormattedCurrency(response.data.data.totalTxnAmount.toString())}"
                    // state.totalMerchantSpent = state.currencyType + " ${"2,02929.00"}"
                    state.setUpStringForMerchant(
                        state.currencyType,
                        Utils.getFormattedCurrency(state.monthlyMerchantAvgAmount)
                    )

                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
        /*merchantList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        merchantList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        merchantList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        merchantList.add(
            TxnAnalytic(
                "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                "Amazon",
                "887.12",
                20.00,
                24
            )
        )
        merchantList.add(
                TxnAnalytic(
                    "https://yap-live.s3.eu-west-1.amazonaws.com/amazon.png",
                    "Amazon",
                    "887.12",
                    20.00,
                    24
                )
                )


           parentVM?.merchantAnalyticsItemLiveData?.value = merchantList*/

    }
}