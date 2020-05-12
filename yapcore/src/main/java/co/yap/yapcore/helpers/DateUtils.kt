package co.yap.yapcore.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private const val DEFAULT_DATE_FORMAT: String = "dd/MM/yyyy"
    val GMT = TimeZone.getTimeZone("GMT")
    val UTC = TimeZone.getTimeZone("UTC")
    val TIME_ZONE_Default = TimeZone.getDefault()
    val FORMAT_LONG_OUTPUT = "MMM dd, yyyy・hh:mm a"//2015-11-28 10:17:18//2016-12-12 12:23:00
    val FORMAT_LONG_INPUT = "yyyy-MM-dd'T'HH:mm:ss"//2015-11-28 10:17:18
    val LeanPlumEventFormat = "yyyy-MM-dd HH:mm:ss"//2015-11-28 10:17:18
    val FORMAT_MON_YEAR = "MMMM yyyy"//2015-11-28 10:17:18
    val FORMAT_MONTH_YEAR = "MMMM, yyyy"//2015-11-28 10:17:18
    val FORMAT_DATE_MON_YEAR = "MMMM dd, yyyy"//2015-11-28 10:17:18
    val LEANPLUM_FORMATOR = "dd MMMM, yyyy"
    val FORMATE_TIME_24H = "hh:mm a"
//2020-02-16T13:20:05
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

    /*fun stringToDate(date: String, formatter: String? = DEFAULT_DATE_FORMAT): Date? {
        val format = SimpleDateFormat(formatter, Locale.US)
        var convertedDate: Date? = null
        try {
            convertedDate = format.parse(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return convertedDate
    }*/

    fun reformatStringDate(
        date: String,
        inputFormatter: String? = DEFAULT_DATE_FORMAT,
        outFormatter: String? = DEFAULT_DATE_FORMAT
    ): String {
        var result = ""
        val formatter = SimpleDateFormat(outFormatter, Locale.getDefault())
        try {
            // formatter.timeZone = TIME_ZONE_Default
            result = formatter.format(stringToDate(date, inputFormatter ?: ""))
        } catch (e: Exception) {
        }
        return result
    }

    fun reformatStringDate(
        date: String?,
        inputFormatter: String? = DEFAULT_DATE_FORMAT,
        outFormatter: String? = DEFAULT_DATE_FORMAT,
        inputTimeZone: TimeZone = GMT,
        outTimeZone: TimeZone = TIME_ZONE_Default
    ): String {
        var result = ""
        date?.let {
            try {
                val formatter = SimpleDateFormat(outFormatter, Locale.getDefault())
                formatter.timeZone = outTimeZone
                result = formatter.format(
                    stringToDate(
                        dateStr = it,
                        format = inputFormatter,
                        timeZone = inputTimeZone
                    )
                )
            } catch (e: Exception) {
            }
        }
        return result

    }


    fun dateToString(day: Int, month: Int, year: Int, format: String = DEFAULT_DATE_FORMAT) =
        SimpleDateFormat(format, Locale.US).format(toDate(day, month, year))

    fun dateToString(date: Date?, format: String = DEFAULT_DATE_FORMAT) = datetoString(date, format)

    fun datetoString(date: Date?, format: String, timeZone: TimeZone = TIME_ZONE_Default): String {
        date?.let {
            var result = ""
            val formatter = SimpleDateFormat(format, Locale.US)
            formatter.timeZone = timeZone
//            val symbols = DateFormatSymbols(Locale.getDefault())
//            symbols.amPmStrings = arrayOf("am", "pm")
//            formatter.dateFormatSymbols = symbols
            try {
                result = formatter.format(it)
            } catch (e: Exception) {
            }

            return result
        } ?: return ""

    }

    fun stringToDate(dateStr: String, format: String): Date? {
        var d: Date? = null
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        formatter.timeZone = UTC
        try {
            formatter.isLenient = false
            d = formatter.parse(dateStr)
            formatter.timeZone = TIME_ZONE_Default
            val newDate = SimpleDateFormat(format, Locale.getDefault()).format(d)
            d = formatter.parse(newDate)
        } catch (e: Exception) {
            d = null
        }
        return d
    }

    fun stringToDate(dateStr: String, format: String?, timeZone: TimeZone = GMT): Date? {
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


    fun convertTopUpDate(creationDate: String, parser: SimpleDateFormat): String? {
        val parser = SimpleDateFormat("MMyy")
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val convertedDate = parser.parse(creationDate)
        val pattern = "MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(convertedDate)
    }

    fun stringToDateLeanPlum(dateStr: String): Date? {
        var d: Date? = null
        val formatter = SimpleDateFormat(LEANPLUM_FORMATOR, Locale.getDefault())
        formatter.timeZone = GMT
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


    fun isDatePassed(creationDate: String, parser: SimpleDateFormat): Boolean {
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val convertedDate = parser.parse(creationDate)
        return isDatePassed(convertedDate)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDateWithFormat(formal: String): String {
        val sdf = SimpleDateFormat(formal)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date())
    }

//    @SuppressLint("SimpleDateFormat")
//    fun getCurrentDate(): Date {
//        val sdf = SimpleDateFormat("yyyy-MM-dd")
//        return sdf.format(Date())
//    }

    @SuppressLint("SimpleDateFormat")
    fun convertAnalyticsDate(creationDate: String?): String? {
        return try {
            val parser = SimpleDateFormat("yyyy-MM")
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val convertedDate = parser.parse(creationDate)
            val pattern = "MMMM, yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            simpleDateFormat.format(convertedDate)
        } catch (ex: Exception) {
            ""
        }

    }

    fun dayDiff(date1: Date, date2: Date) = (date2.time - date1.time) / 86400000
    fun dayDiffFromCurrent(date: Date): Long {
        return (date.time - Calendar.getInstance().time.time) / 86400000
    }

}