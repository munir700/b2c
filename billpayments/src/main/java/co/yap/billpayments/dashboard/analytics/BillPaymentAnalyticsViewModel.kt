package co.yap.billpayments.dashboard.analytics

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.R
import co.yap.billpayments.dashboard.analytics.adapter.BPAnalyticsAdapter
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsResponseDTO
import co.yap.widgets.pieview.PieEntry
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import java.util.*

class BillPaymentAnalyticsViewModel(application: Application) :
    BaseViewModel<IBillPaymentAnalytics.State>(application),
    IBillPaymentAnalytics.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: BillPaymentAnalyticsState = BillPaymentAnalyticsState()
    override val repository: TransactionsRepository = TransactionsRepository
    private var currentDate: Date? = Date()
    private var listOfMonths: List<Date> = arrayListOf()
    override val analyticsAdapter: BPAnalyticsAdapter = BPAnalyticsAdapter(arrayListOf())
    override val analyticsData: MutableLiveData<List<BPAnalyticsModel>> = MutableLiveData()
    override fun handlePressOnView(id: Int) {
        when (id) {
            R.id.ivPrevious -> gotoPreviousMonth()
            R.id.ivNext -> gotoNextMonth()
            else -> clickEvent.setValue(id)
        }
    }

    override fun onCreate() {
        super.onCreate()
        initCurrentDate()
    }

    private fun initCurrentDate() {
        val startDate = SessionManager.user?.creationDate ?: ""
        val endDate = DateUtils.dateToString(
            Date(),
            DateUtils.SIMPLE_DATE_FORMAT, DateUtils.TIME_ZONE_Default
        )
        listOfMonths = DateUtils.geMonthsBetweenTwoDates(
            startDate,
            endDate
        )
        fetchAnalytics(currentDate)
        setSelectedDate(currentDate)
        state.previousMonth.set(isPreviousIconEnabled(listOfMonths, currentDate))
    }

    private fun gotoPreviousMonth() {
        currentDate = DateUtils.getPriviousMonthFromCurrentDate(
            listOfMonths,
            currentDate
        )
        fetchAnalytics(currentDate)
        state.previousMonth.set(isPreviousIconEnabled(listOfMonths, currentDate))
        state.nextMonth.set(true)
    }

    private fun gotoNextMonth() {
        currentDate = DateUtils.getNextMonthFromCurrentDate(
            listOfMonths,
            currentDate
        )
        fetchAnalytics(currentDate)

        state.nextMonth.set(isNextIconEnabled(listOfMonths, currentDate))
        state.previousMonth.set(true)
    }


    private fun fetchAnalytics(currentDate: Date?) {
        setSelectedDate(currentDate)
        launch {
            state.viewState.value = true
            delay(200)
            analyticsData.value = mockAnalytics().data
            analyticsAdapter.setList(analyticsData.value!!)
            state.viewState.value = false
        }
//        launch(Dispatcher.Background) {
//            state.viewState.postValue(true)
//            val response = repository.getBPAnalytics(date = DateUtils.reformatToLocalString(
//                    currentDate,
//                    DateUtils.SIMPLE_DATE_FORMAT
//            ))
//            launch(Dispatcher.Main) {
//                when (response) {
//                    is RetroApiResponse.Success -> {
//                        state.viewState.value = false
//                    }
//                    is RetroApiResponse.Error -> {
//                        state.viewState.value = false
//                        showToast(response.error.message)
//                    }
//                }
//            }
//        }
    }

    private fun mockAnalytics(): BPAnalyticsResponseDTO {
        val gson = GsonBuilder().create()
        return gson.fromJson<BPAnalyticsResponseDTO>(
            context.getJsonDataFromAsset(
                "jsons/analytics.json"
            ), object : TypeToken<BPAnalyticsResponseDTO>() {}.type
        )
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

    private fun setSelectedDate(currentDate: Date?) {
        val displayDate =
            currentDate?.let {
                DateUtils.dateToString(currentDate, DateUtils.FORMAT_MON_YEAR, false)
            } ?: ""
        state.displayMonth.set(displayDate)
        state.selectedMonth = displayDate
//        parentViewModel?.state?.currentSelectedMonth = state.selectedMonth ?: ""
//        parentViewModel?.state?.currentSelectedDate =
//            DateUtils.dateToString(currentDate, DateUtils.SIMPLE_DATE_FORMAT, false)
    }

    override fun getEntries(it: List<BPAnalyticsModel>?): ArrayList<PieEntry> {
        val entries = ArrayList<PieEntry>()
        if (it.isNullOrEmpty())
            entries.add(PieEntry(100f))
        else {
            for (item in it.iterator())
                item.totalSpendingInPercentage?.toFloat()?.let { PieEntry(it) }?.let {
                    entries.add(it)
                }
        }
        return entries
    }

    override fun setSelectedItemState(
        model: BPAnalyticsModel,
        currentPosition: Int
    ) {
        // update selection of item
        val previousPosition: Int = analyticsAdapter.selectedItem
        analyticsAdapter.selectedItem = currentPosition
        analyticsAdapter.notifyItemChanged(previousPosition)
        analyticsAdapter.notifyItemChanged(currentPosition)

        // update selected data in pie-chart
        state.selectedItemSpentValue =
            model.totalSpending.toFormattedCurrency(
                showCurrency = true,
                withComma = true
            )
        state.selectedItemName = model.categoryName
    }
}
