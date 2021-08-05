package co.yap.billpayments.utils

import co.yap.yapcore.helpers.DateUtils
import java.util.*

class MonthYearHandler(availableMonthList: List<Date>, currDate: Date = Date()) {

    var currentDate: Date? = null
    private var listOfMonths: List<Date> = arrayListOf()

    init {
        this.listOfMonths = availableMonthList
        this.currentDate = currDate
    }

    fun previousMonth(currentDate: Date?): Date? {
        val currDate = DateUtils.getPriviousMonthFromCurrentDate(
            listOfMonths,
            currentDate
        )
        this.currentDate = currDate
        return this.currentDate
    }

    fun nextMonth(currentDate: Date?): Date? {
        val currDate = DateUtils.getNextMonthFromCurrentDate(
            listOfMonths,
            currentDate
        )
        this.currentDate = currDate
        return this.currentDate
    }

    fun isPreviousIconEnabled(currentDate: Date?): Boolean {
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

    fun isNextIconEnabled(currentDate: Date?): Boolean {
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