package co.yap.billpayments.dashboard.analytics

import android.app.Application
import co.yap.billpayments.R
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.firebase.FirebaseEvent
import co.yap.yapcore.firebase.trackEventWithScreenName
import co.yap.yapcore.helpers.DateUtils
import co.yap.yapcore.managers.SessionManager
import java.util.*

class BillPaymentAnalyticsViewModel(application: Application) :
        BaseViewModel<IBillPaymentAnalytics.State>(application),
        IBillPaymentAnalytics.ViewModel {
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: BillPaymentAnalyticsState = BillPaymentAnalyticsState()
    private var currentDate: Date? = Date()
    private var listOfMonths: List<Date> = arrayListOf()
    override fun onCreate() {
        super.onCreate()
        initCurrentDate()
    }

    override fun handlePressOnView(id: Int) {
        when (id) {
            R.id.ivPrevious -> {
                currentDate = DateUtils.getPriviousMonthFromCurrentDate(
                        listOfMonths,
                        currentDate
                )
//                fetchAnalytics(currentDate)
                state.previousMonth.set(isPreviousIconEnabled(listOfMonths, currentDate))
                state.nextMonth.set(true)
                trackEventWithScreenName(FirebaseEvent.SCROLL_DATES)
            }
            R.id.ivNext -> {
                currentDate = DateUtils.getNextMonthFromCurrentDate(
                        listOfMonths,
                        currentDate
                )
//                fetchAnalytics(currentDate)
                state.nextMonth.set(isNextIconEnabled(listOfMonths, currentDate))
                state.previousMonth.set(true)
                trackEventWithScreenName(FirebaseEvent.SCROLL_DATES)

            }
            else -> clickEvent.setValue(id)
        }
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
        setSelectedDate(currentDate)
        state.previousMonth.set(isPreviousIconEnabled(listOfMonths, currentDate))
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
                    DateUtils.dateToString(currentDate, DateUtils.FORMATE_DATE_MONTH_YEAR_ENG, false)
                } ?: ""
        state.displayMonth.set(displayDate)

        state.selectedMonth =
                DateUtils.dateToString(currentDate, DateUtils.FORMAT_MON_YEAR, false)
//        parentViewModel?.state?.currentSelectedMonth = state.selectedMonth ?: ""
//        parentViewModel?.state?.currentSelectedDate =
//            DateUtils.dateToString(currentDate, DateUtils.SIMPLE_DATE_FORMAT, false)
    }
}
