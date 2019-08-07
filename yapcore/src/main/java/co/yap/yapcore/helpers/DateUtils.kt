package co.yap.yapcore.helpers

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    fun getAge(date: Date): Int {
        val today = Calendar.getInstance()
        val dob = Calendar.getInstance()
        dob.time = date

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }


    fun getAge(day: Int, month: Int, year: Int): Int = getAge(toDate(day, month, year))

    fun isDatePassed(date: Date): Boolean = date.before(Date())

    fun toDate(day: Int, month: Int, year: Int): Date {
        return if (year.toString().length == 2) {
            normaliseDate(day, month, year)
        } else {
            val cal = Calendar.getInstance()
            cal.set(Calendar.DAY_OF_MONTH, day)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.YEAR, year)
            cal.time
        }

    }

    fun normaliseDate(day: Int, month: Int, year: Int): Date {
        val dd = if (day < 10) "0$day" else "" + day
        val mm = if (month < 10) "0$month" else "" + month
        val yy = if (year < 10) "0$year" else "" + year
        val format = SimpleDateFormat("dd-mm-yy", Locale.UK)
        return format.parse("$dd-$mm-$yy")
    }

    fun stringToDate(date: String, formatter: String? = "dd-mm-yyyy"): Date? {
        val format = SimpleDateFormat(formatter, Locale.UK)
        var convertedDate: Date? = null
        try {
            convertedDate = format.parse(date)
            println(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertedDate
    }

}