package co.yap.yapcore.helpers

import co.yap.yapcore.managers.SessionManager
import java.util.*

class MonthYearHandler {
    private var currentDate: Date? = Date()
    private var listOfMonths: List<Date> = arrayListOf()

    init {
        val startDate = SessionManager.user?.creationDate ?: ""
        val endDate = DateUtils.dateToString(
            Date(),
            DateUtils.SIMPLE_DATE_FORMAT, DateUtils.TIME_ZONE_Default
        )
        listOfMonths = DateUtils.geMonthsBetweenTwoDates(
            startDate,
            endDate
        )
//        setSelectedDate(currentDate)
//        state.previousMonth.set(isPreviousIconEnabled(listOfMonths, currentDate))
    }

    private fun previousMonth(): Boolean {
        currentDate = DateUtils.getPriviousMonthFromCurrentDate(
            listOfMonths,
            currentDate
        )
//        fetchAnalytics(currentDate)
        return isPreviousIconEnabled(listOfMonths, currentDate)
//        state.previousMonth.set(isPreviousIconEnabled(listOfMonths, currentDate))
//        state.nextMonth.set(true)
    }

    private fun nextMonth(): Boolean {
        currentDate = DateUtils.getNextMonthFromCurrentDate(
            listOfMonths,
            currentDate
        )
//        fetchAnalytics(currentDate)
        return isNextIconEnabled(listOfMonths, currentDate)

//        state.nextMonth.set(isNextIconEnabled(listOfMonths, currentDate))
//        state.previousMonth.set(true)
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
}