package co.yap.yapcore.helpers

import android.annotation.SuppressLint
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtils {

    private const val DEFAULT_DATE_FORMAT: String = "dd/MM/yyyy"
    val GMT = TimeZone.getTimeZone("GMT")
    val TIME_ZONE_Default = TimeZone.getDefault()
    val FORMAT_LONG_OUTPUT = "MMM dd, YYYY・HH:mma"//2015-11-28 10:17:18//2016-12-12 12:23:00
    val FORMAT_LONG_INPUT = "yyyy-MM-dd'T'HH:mm:ss"//2015-11-28 10:17:18

//    Jan 29, 2019・10:35am

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
        val format = SimpleDateFormat("dd-mm-yy", Locale.US)
        return format.parse("$dd-$mm-$yy")
    }

    fun stringToDate(date: String, formatter: String? = DEFAULT_DATE_FORMAT): Date? {
        val format = SimpleDateFormat(formatter, Locale.US)
        var convertedDate: Date? = null
        try {
            convertedDate = format.parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertedDate
    }

    fun dateToString(day: Int, month: Int, year: Int, format: String = DEFAULT_DATE_FORMAT) =
        SimpleDateFormat(format, Locale.US).format(toDate(day, month, year))

    fun dateToString(date: Date?, format: String = DEFAULT_DATE_FORMAT): String {

        try {
            return SimpleDateFormat(format, Locale.US).format(date)

        } catch (e: Exception) {
            return " ";
        }

    }

    fun datetoString(date: Date?, format: String, timeZone: TimeZone = TIME_ZONE_Default): String {
        var result = ""
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.timeZone = timeZone
        val symbols = DateFormatSymbols(Locale.getDefault())
        symbols.amPmStrings = arrayOf("am", "pm")
        formatter.dateFormatSymbols = symbols
        try {
            result = formatter.format(date!!)
        } catch (e: Exception) {
        }

        return result
    }

    fun stringToDate(dateStr: String, format: String, timeZone: TimeZone = GMT): Date? {
        var d: Date? = null
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.timeZone = timeZone
        try {
            formatter.isLenient = false
            d = formatter.parse(dateStr)
        } catch (e: Exception) {
            d = null
        }

        return d
    }

    @SuppressLint("SimpleDateFormat")
    fun convertTopUpDate(creationDate: String?): String? {
        try {
            val parser = SimpleDateFormat("MMyy")
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val convertedDate = parser.parse(creationDate)
            val pattern = "MM/yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            return simpleDateFormat.format(convertedDate)
        } catch (ex: Exception) {
            return ""
        }

    }

    fun convertTopUpDate(creationDate: String, parser: SimpleDateFormat): String? {
        val parser = SimpleDateFormat("MMyy")
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val convertedDate = parser.parse(creationDate)
        val pattern = "MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(convertedDate)
    }


    fun isDatePassed(creationDate: String, parser: SimpleDateFormat): Boolean {
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val convertedDate = parser.parse(creationDate)
        return isDatePassed(convertedDate)
    }
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate():String{
        val sdf = SimpleDateFormat("YYYY-MM-dd")
        return sdf.format(Date())
    }

}