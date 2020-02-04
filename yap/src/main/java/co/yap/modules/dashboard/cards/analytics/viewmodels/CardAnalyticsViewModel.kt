package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalytics
import co.yap.modules.dashboard.cards.analytics.main.interfaces.ICardAnalyticsMain
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.TxnAnalytic
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_MONTH_YEAR
import co.yap.yapcore.helpers.DateUtils.FORMAT_MON_YEAR
import co.yap.yapcore.helpers.DateUtils.TIME_ZONE_Default
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
    var currentCalendar: Calendar = Calendar.getInstance()
    var creationCalender: Calendar = Calendar.getInstance()

    override fun onCreate() {
        super.onCreate()
        parentVM?.let {
            parentViewModel = it
        }
        DateUtils.datetoString(currentCalendar.time, "yyyy-MM-dd")
        fetchCardCategoryAnalytics(DateUtils.datetoString(currentCalendar.time, "yyyy-MM-dd"))
        state.nextMonth = false
        MyUserManager.user?.creationDate?.let {
            val date =
                DateUtils.stringToDate(
                    it,
                    DateUtils.FORMAT_LONG_INPUT, TIME_ZONE_Default
                )
            state.selectedMonth = DateUtils.dateToString(currentCalendar.time, FORMAT_MONTH_YEAR)
            date?.let { it ->
                creationCalender.time = it
                if (creationCalender.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH)) {
                    state.previousMonth = false
                } else {
                    if (creationCalender.get(Calendar.MONTH) < currentCalendar.get(Calendar.MONTH)) {
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
        when (id) {
            R.id.ivPrevious -> {

                if ((currentCalendar.get(Calendar.MONTH) - 1) > creationCalender.get(Calendar.MONTH)) {
                    currentCalendar.add(Calendar.MONTH, -1)
                    state.nextMonth = true
                } else if ((currentCalendar.get(Calendar.MONTH) - 1) == creationCalender.get(
                        Calendar.MONTH
                    )
                ) {
                    currentCalendar.add(Calendar.MONTH, -1)
                    state.previousMonth = false
                    // // Proper testing remaining
                    state.nextMonth = true
                }
                state.selectedMonth =
                    DateUtils.dateToString(currentCalendar.time, FORMAT_MONTH_YEAR)
                fetchCardCategoryAnalytics(
                    DateUtils.datetoString(
                        currentCalendar.time,
                        "yyyy-MM-dd"
                    )
                )
            }
            R.id.ivNext -> {
                val tempCalendar = Calendar.getInstance()
                if ((tempCalendar.get(Calendar.MONTH) - 1) > (currentCalendar.get(Calendar.MONTH))) {
                    currentCalendar.add(Calendar.MONTH, 1)
                    state.previousMonth = true
                } else if ((tempCalendar.get(Calendar.MONTH) - 1) == currentCalendar.get(Calendar.MONTH)) {
                    currentCalendar.add(Calendar.MONTH, 1)
                    state.nextMonth = false
                    // Proper testing remaining
                    state.previousMonth = true
                }
                state.selectedMonth =
                    DateUtils.dateToString(currentCalendar.time, FORMAT_MONTH_YEAR)
                fetchCardCategoryAnalytics(
                    DateUtils.datetoString(
                        currentCalendar.time,
                        "yyyy-MM-dd"
                    )
                )
            }
        }
        clickEvent.setValue(id)
    }

    override fun fetchCardCategoryAnalytics(currentMonth: String) {
        val categoryList = ArrayList<TxnAnalytic>()
        //call api here
        launch {
            state.loading = true
            when (val response = repository.getAnalyticsByCategoryName(
                MyUserManager.getCardSerialNumber(), currentMonth
            )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let {
                        state.monthlyCategoryAvgAmount =
                            response.data.data?.monthlyAvgAmount?.toString()
                        state.setUpString(
                            state.currencyType,
                            Utils.getFormattedCurrency(state.monthlyCategoryAvgAmount)
                        )
                        state.totalCategorySpent =
                            state.currencyType + " ${Utils.getFormattedCurrency(response.data.data?.totalTxnAmount.toString())}"
                        state.totalSpent = state.totalCategorySpent
                        clickEvent.postValue(Constants.CATEGORY_AVERAGE_AMOUNT_VALUE)
                        parentVM?.categoryAnalyticsItemLiveData?.value = it.txnAnalytics
                    }

                    fetchCardMerchantAnalytics(currentMonth)
//                    state.loading = false

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

    override fun fetchCardMerchantAnalytics(currentMonth: String) {
//        val merchantList = ArrayList<TxnAnalytic>()
        launch {
            when (val response = repository.getAnalyticsByMerchantName(
                MyUserManager.getCardSerialNumber(),
                currentMonth
            )) {
                is RetroApiResponse.Success -> {
                    state.monthlyMerchantAvgAmount =
                        response.data.data?.monthlyAvgAmount?.toString()
                    state.totalMerchantSpent =
                        state.currencyType + " ${Utils.getFormattedCurrency(response.data.data?.totalTxnAmount.toString())}"
                    state.setUpStringForMerchant(
                        state.currencyType,
                        Utils.getFormattedCurrency(state.monthlyMerchantAvgAmount)
                    )
                    parentVM?.merchantAnalyticsItemLiveData?.value =
                        response.data.data?.txnAnalytics
                    state.loading = false
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            //state.loading = false
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