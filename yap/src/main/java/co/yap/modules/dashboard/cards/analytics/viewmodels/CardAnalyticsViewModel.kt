package co.yap.modules.dashboard.cards.analytics.viewmodels

import android.app.Application
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.modules.dashboard.cards.analytics.interfaces.ICardAnalytics
import co.yap.modules.dashboard.cards.analytics.main.viewmodels.CardAnalyticsBaseViewModel
import co.yap.modules.dashboard.cards.analytics.models.AnalyticsItem
import co.yap.modules.dashboard.cards.analytics.states.CardAnalyticsState
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Strings
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.DateUtils.FORMAT_MONTH_YEAR
import co.yap.yapcore.helpers.DateUtils.SIMPLE_DATE_FORMAT
import co.yap.yapcore.helpers.ImageBinding
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager
import java.text.SimpleDateFormat
import java.util.*

class CardAnalyticsViewModel(application: Application) :
    CardAnalyticsBaseViewModel<ICardAnalytics.State>(application = application),
    ICardAnalytics.ViewModel {
    override val state: CardAnalyticsState = CardAnalyticsState(application)
    override var selectedModel: MutableLiveData<AnalyticsItem> = MutableLiveData()
    val repository: TransactionsRepository = TransactionsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    private var listOfMonths: List<Date> = arrayListOf()
//    override var type: ObservableField<String> = ObservableField("merchant-category-id")

    override fun onCreate() {
        super.onCreate()
        setToolBarTitle(getString(Strings.screen_card_analytics_tool_bar_title))
        val startDate = SessionManager.user?.creationDate ?: ""
        val endDate = DateUtils.dateToString(
            Date(),
            SIMPLE_DATE_FORMAT, DateUtils.TIME_ZONE_Default
        )
        listOfMonths = DateUtils.geMonthsBetweenTwoDates(
            startDate,
            endDate
        )
    }

    override fun handlePressOnView(id: Int) {
        when (id) {
            R.id.ivPrevious -> {
                parentViewModel?.currentDate = DateUtils.getPriviousMonthFromCurrentDate(
                    listOfMonths,
                    parentViewModel?.currentDate
                )
                fetchAnalytics(parentViewModel?.currentDate)
                state.previousMonth =
                    isPreviousIconEnabled(listOfMonths, parentViewModel?.currentDate)
                state.nextMonth = true
                trackEventWithScreenName(FirebaseEvent.SCROLL_DATES)
            }
            R.id.ivNext -> {
                parentViewModel?.currentDate = DateUtils.getNextMonthFromCurrentDate(
                    listOfMonths,
                    parentViewModel?.currentDate
                )
                fetchAnalytics(parentViewModel?.currentDate)
                state.nextMonth = isNextIconEnabled(listOfMonths, parentViewModel?.currentDate)
                state.previousMonth = true
                trackEventWithScreenName(FirebaseEvent.SCROLL_DATES)

            }
        }
        clickEvent.setValue(id)
    }

    override fun fetchCardCategoryAnalytics(currentMonth: String) {
        parentViewModel?.categoryAnalyticsItemLiveData?.value?.clear()
        launch {
            state.loading = true
            when (val response = repository.getAnalyticsByCategoryName(
                currentMonth
            )) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { analyticsDTO ->
                        state.monthlyCategoryAvgAmount =
                            response.data.data?.monthlyAvgAmount?.toString()

                        state.totalCategorySpent = response.data.data?.totalTxnAmount.toString()
                            .toFormattedCurrency(
                                showCurrency = true,
                                currency = state.currencyType ?: SessionManager.getDefaultCurrency()
                            )
                        state.totalSpent = state.totalCategorySpent
                        clickEvent.postValue(Constants.CATEGORY_AVERAGE_AMOUNT_VALUE)
                        parentViewModel?.categoryAnalyticsItemLiveData?.value =
                            analyticsDTO.txnAnalytics
                        parentViewModel?.state?.isNoDataFound?.set(isDataAvailableForSelectedMonth(0))

                    }

                    fetchCardMerchantAnalytics(currentMonth)

                }
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message

                }
            }
        }

    }

    override fun fetchCardMerchantAnalytics(currentMonth: String) {
        parentViewModel?.merchantAnalyticsItemLiveData?.value?.clear()
        launch {
            when (val response = repository.getAnalyticsByMerchantName(currentMonth)) {
                is RetroApiResponse.Success -> {
                    response.data.data?.let { merchantResponse ->
                        state.monthlyMerchantAvgAmount =
                            merchantResponse.monthlyAvgAmount?.toString()
                        state.totalMerchantSpent = merchantResponse.totalTxnAmount.toString()
                            .toFormattedCurrency(
                                showCurrency = true,
                                currency = state.currencyType ?: SessionManager.getDefaultCurrency()
                            )

                        parentViewModel?.merchantAnalyticsItemLiveData?.value =
                            merchantResponse.txnAnalytics

                    }
                    state.loading = false
                    parentViewModel?.state?.isNoDataFound?.set(isDataAvailableForSelectedMonth(1))
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
        }
    }

    override fun isDataAvailableForSelectedMonth(tab: Int): Boolean {
        return when {
            tab == 0 && parentViewModel?.categoryAnalyticsItemLiveData?.value?.size == 0 -> false
            tab == 1 && parentViewModel?.merchantAnalyticsItemLiveData?.value?.size == 0 -> false
            else -> true
        }

    }

    private fun isPreviousIconEnabled(listOfMonths: List<Date>, currentDate: Date?): Boolean {
        var index: Int = -1
        currentDate?.let {
            for (i in listOfMonths.indices) {
                if (DateUtils.isDateMatched(listOfMonths[i], currentDate)) {
                    index = i
                    break
                }
            }
        }

        return index - 1 >= 0
    }

    private fun isNextIconEnabled(listOfMonths: List<Date>, currentDate: Date?): Boolean {
        var index: Int = -1
        currentDate?.let {
            for (i in listOfMonths.indices) {
                if (DateUtils.isDateMatched(listOfMonths[i], currentDate)) {
                    index = i
                    break
                }
            }
        }

        return listOfMonths.size >= index + 2
    }

    private fun fetchAnalytics(currentDate: Date?) {
        if (currentDate != null) {
            fetchCardCategoryAnalytics(
                DateUtils.reformatToLocalString(
                    currentDate,
                    SIMPLE_DATE_FORMAT
                )
            )
            setSelectedDate(currentDate)
        }
    }

    private fun setSelectedDate(currentDate: Date?) {
        state.displayMonth = currentDate?.let { DateUtils.getMonth(it) } ?: ""
        state.selectedMonth = DateUtils.dateToString(currentDate, FORMAT_MONTH_YEAR, false)
        parentViewModel?.state?.currentSelectedMonth = state.selectedMonth ?: ""
        parentViewModel?.state?.currentSelectedDate =
            DateUtils.dateToString(currentDate, SIMPLE_DATE_FORMAT, false)
    }

    override fun setPieChartIcon(image: ImageView) {
        ImageBinding.loadCategoryAvatar(
            image,
            state.selectedTxnAnalyticsItem.get()?.logoUrl ?: "",
            state.selectedTxnAnalyticsItem.get()?.title ?: "",
            state.selectedItemPosition.get(),
            isBackground = false,
            showFirstInitials = false,
            categoryColor = ""
        )
    }

    override fun setCurrentMonthCall() {
        fetchCardCategoryAnalytics(
            DateUtils.dateToString(
                Calendar.getInstance().time,
                SIMPLE_DATE_FORMAT, DateUtils.TIME_ZONE_Default
            )
        )
        setDateAndMonthsEnableStates(Date())
        setSelectedDate(parentViewModel?.currentDate)
    }

    override fun fetchCardCategoryAnalyticsByDate() {
        if(parentViewModel?.currentDate == null){
            with(parentViewModel?.state) {
                this?.selectedDate?.let {
                    if (it.isNotEmpty()) {
                        SimpleDateFormat(
                            DateUtils.FORMAT_COMPLETE_DATE,
                            Locale.getDefault()
                        ).parse(it)?.let { date ->
                            fetchCardCategoryAnalytics(
                                DateUtils.dateToString(
                                    date, SIMPLE_DATE_FORMAT, DateUtils.TIME_ZONE_Default
                                )
                            )
                            setDateAndMonthsEnableStates(date)
                            setSelectedDate(parentViewModel?.currentDate)
                        }
                    } else setCurrentMonthCall()
                } ?: setCurrentMonthCall()
            }
        }
    }

    override fun setDateAndMonthsEnableStates(date: Date?) {
        parentViewModel?.currentDate = date
        state.previousMonth = isPreviousIconEnabled(listOfMonths, parentViewModel?.currentDate)
        state.nextMonth = isNextIconEnabled(listOfMonths, parentViewModel?.currentDate)
    }
}