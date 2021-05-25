package co.yap.billpayments.dashboard.analytics

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.billpayments.R
import co.yap.billpayments.base.BillDashboardBaseViewModel
import co.yap.billpayments.dashboard.analytics.adapter.BPAnalyticsAdapter
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsModel
import co.yap.networking.transactions.responsedtos.billpayments.BPAnalyticsResponseDTO
import co.yap.widgets.pieview.PieEntry
import co.yap.yapcore.Dispatcher
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.BillCategory
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.helpers.MonthYearHandler
import co.yap.yapcore.helpers.extentions.getColors
import co.yap.yapcore.helpers.extentions.getJsonDataFromAsset
import co.yap.yapcore.helpers.extentions.parseToDouble
import co.yap.yapcore.helpers.extentions.toFormattedCurrency
import co.yap.yapcore.managers.SessionManager
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*

class BillPaymentAnalyticsViewModel(application: Application) :
    BillDashboardBaseViewModel<IBillPaymentAnalytics.State>(application),
    IBillPaymentAnalytics.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: BillPaymentAnalyticsState = BillPaymentAnalyticsState()
    override val repository: TransactionsRepository = TransactionsRepository
    override val analyticsAdapter: BPAnalyticsAdapter = BPAnalyticsAdapter(arrayListOf())
    override val analyticsData: MutableLiveData<List<BPAnalyticsModel>> = MutableLiveData()
    private var monthYearHandler: MonthYearHandler? = null
    override fun handlePressOnView(id: Int) {
        when (id) {
            R.id.ivPrevious -> gotoPreviousMonth()
            R.id.ivNext -> gotoNextMonth()
            else -> clickEvent.setValue(id)
        }
    }

    init {
        val startDate = SessionManager.user?.creationDate ?: ""
        val endDate = DateUtils.dateToString(
                Date(),
                DateUtils.SIMPLE_DATE_FORMAT, DateUtils.TIME_ZONE_Default
        )
        val listOfMonths = DateUtils.geMonthsBetweenTwoDates(
                startDate,
                endDate
        )
        monthYearHandler = MonthYearHandler(listOfMonths)
    }

    override fun onCreate() {
        super.onCreate()
        setToolBarTitle("Analytics")
    }

    override fun initCurrentDate() {
        setSelectedDate(monthYearHandler?.currentDate)
        state.previousMonth.set(monthYearHandler?.isPreviousIconEnabled(monthYearHandler?.currentDate) == true)
    }

    private fun gotoPreviousMonth() {
        val date: Date? = monthYearHandler?.previousMonth(monthYearHandler?.currentDate)
        fetchAnalytics(forDate = date)
        state.previousMonth.set(monthYearHandler?.isPreviousIconEnabled(date) == true)
        state.nextMonth.set(true)
    }

    private fun gotoNextMonth() {
        val date = monthYearHandler?.nextMonth(monthYearHandler?.currentDate)
        fetchAnalytics(forDate = date)
        state.nextMonth.set(monthYearHandler?.isNextIconEnabled(date) == true)
        state.previousMonth.set(true)
    }


    private fun fetchAnalytics(forDate: Date?) {
        setSelectedDate(forDate)
        val dateRequiredByApi =
                state.displayMonth.get()?.split(" ")?.joinToString(separator = ",") {
                    it
                }
        fetchBillCategoryAnalytics(currentMonth = dateRequiredByApi ?: "")
    }

    override fun fetchBillCategoryAnalytics(currentMonth: String) {
        launch(Dispatcher.Background) {
            state.viewState.postValue(true)
            val response = repository.getBPAnalytics(currentMonth)
            launch(Dispatcher.Main) {
                when (response) {
                    is RetroApiResponse.Success -> {
                        analyticsData.value = response.data.data
                        analyticsAdapter.setList(analyticsData.value ?: arrayListOf())
                        state.viewState.value = false
                    }
                    is RetroApiResponse.Error -> {
                        state.viewState.value = false
                        showToast(response.error.message)
                    }
                }
            }
        }
    }


    private fun setSelectedDate(currentDate: Date?) {
        val displayDate =
            currentDate?.let {
                DateUtils.dateToString(currentDate, DateUtils.FORMAT_MON_YEAR, false)
            } ?: ""
        state.displayMonth.set(displayDate)
        state.selectedMonth = displayDate
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
    override fun getPieChartColors(it: List<BPAnalyticsModel>?): List<Int> {
        if(it.isNullOrEmpty()) return emptyList()
        return it.map {
            when (it.categoryType) {
                BillCategory.CREDIT_CARD.name -> context.getColors(BillCategory.CREDIT_CARD.color)
                BillCategory.TELECOM.name -> context.getColors(BillCategory.TELECOM.color)
                BillCategory.UTILITIES.name -> context.getColors(BillCategory.UTILITIES.color)
                BillCategory.TRANSPORT.name -> context.getColors(BillCategory.TRANSPORT.color)
                else -> -1
            }
        }
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

    override fun getTotalSpentAmountOnBills(it: List<BPAnalyticsModel>?): Double =
        it?.sumByDouble { it.totalSpending?.parseToDouble() ?: 0.0 }?:0.0

}
