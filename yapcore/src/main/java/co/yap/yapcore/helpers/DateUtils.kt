package co.yap.yapcore.helpers

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    const val DEFAULT_DATE_FORMAT: String = "dd/MM/yyyy"
    val GMT: TimeZone = TimeZone.getTimeZone("GMT")
    val UTC: TimeZone = TimeZone.getTimeZone("UTC")
     val TIME_ZONE_Default: TimeZone = TimeZone.getDefault()
    const val FORMAT_LONG_OUTPUT = "MMM dd, yyyy・hh:mm a"//2015-11-28 10:17:18//2016-12-12 12:23:00
    const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm"//2015-11-28 10:17:18
    const val LEAN_PLUM_EVENT_FORMAT = "yyyy-MM-dd HH:mm:ss"//2015-11-28 10:17:18
    const val FORMAT_MON_YEAR = "MMMM yyyy"//2015-11-28 10:17:18
    const val FORMAT_MONTH_YEAR = "MMMM, yyyy"//2015-11-28 10:17:18
    const val FORMAT_DATE_MON_YEAR = "MMMM dd, yyyy"//2015-11-28 10:17:18
    const val FORMAT_DATE_SHORT_MON_YEAR = "MMM dd, yyyy"//2015-11-28 10:17:18
    const val LEAN_PLUM_FORMAT = "dd MMMM, yyyy"
    const val FORMAT_TIME_24H = "HH:mm"
    const val FORMAT_TIME_12H = "hh:mm a"
    const val FXRATE_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm a"//20/11/2020 10:17
    const val FORMATE_MONTH_DAY = "MMM dd" // jan 1
    const val FORMAT_TIME = "hh:mm"

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

    private fun toDate(day: Int, month: Int, year: Int): Date {
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

    private fun normaliseDate(day: Int, month: Int, year: Int): Date {
        val dd = if (day < 10) "0$day" else "" + day
        val mm = if (month < 10) "0$month" else "" + month
        val yy = if (year < 10) "0$year" else "" + year
        val format = SimpleDateFormat("dd-mm-yy", Locale.US)
        return format.parse("$dd-$mm-$yy")
    }

    fun reformatStringDate(
        date: String,
        inputFormatter: String? = DEFAULT_DATE_FORMAT,
        outFormatter: String? = DEFAULT_DATE_FORMAT
    ): String {
        var result = ""
        val formatter = SimpleDateFormat(outFormatter, Locale.US)
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

    fun reformatDate(
        date: String?,
        inputFormatter: String = DEFAULT_DATE_FORMAT,
        outFormatter: String = DEFAULT_DATE_FORMAT,
        inputTimeZone: TimeZone = GMT,
        outTimeZone: TimeZone = TIME_ZONE_Default
    ): String {
        var result = ""
        date?.let {
            try {
                val formatter = SimpleDateFormat(outFormatter, Locale.US)
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

    fun reformatLiveStringDate(
        date: String,
        inputFormatter: String? = DEFAULT_DATE_FORMAT,
        outFormatter: String? = DEFAULT_DATE_FORMAT
    ): String {
        var result = ""
        val formatter = SimpleDateFormat(outFormatter, Locale.US)
        try {
            formatter.timeZone = TIME_ZONE_Default
            result = formatter.format(stringToDate(date, inputFormatter ?: ""))
        } catch (e: Exception) {
        }

        return result

    }

    fun dateToString(date: Date?, format: String = DEFAULT_DATE_FORMAT): String {
        return try {
            SimpleDateFormat(format, Locale.US).format(date)
            val sdf = SimpleDateFormat(format, Locale.US)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            return sdf.format(date)
        } catch (e: Exception) {
            " ";
        }
    }

//    fun dateToString(day: Int, month: Int, year: Int, format: String = DEFAULT_DATE_FORMAT) =
//        SimpleDateFormat(format, Locale.US).format(toDate(day, month, year))

//    fun dateToString(date: Date?, format: String = DEFAULT_DATE_FORMAT) = datetoString(date, format)

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
        val formatter = SimpleDateFormat(format, Locale.US)
        formatter.timeZone = UTC
        try {
            formatter.isLenient = false
            d = formatter.parse(dateStr)
            formatter.timeZone = TIME_ZONE_Default
            val newDate = SimpleDateFormat(format, Locale.US).format(d)
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

    fun formatTime(format: String, date: String): String?{
        val outputFormat: DateFormat = SimpleDateFormat(format, Locale.getDefault())
        val inputFormat: DateFormat = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)
        val date: Date = inputFormat.parse(date)
        return outputFormat.format(date)
    }


    fun convertTopUpDate(creationDate: String, parser: SimpleDateFormat): String? {
        val parser = SimpleDateFormat("MMyy")
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val convertedDate = parser.parse(creationDate)
        val pattern = "MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        return simpleDateFormat.format(convertedDate)
    }

    fun reformatLocalDate(
        date: String,
        inputFormatter: String? = DEFAULT_DATE_FORMAT,
        outputFormatter: String? = DEFAULT_DATE_FORMAT
    ): Date? {
        return try {
            val inFormatter = SimpleDateFormat(inputFormatter, Locale.US)
            val outFormatter = SimpleDateFormat(outputFormatter, Locale.US)
            val newDate = outFormatter.format(inFormatter.parse(date))
            outFormatter.parse(newDate)
        } catch (e: Exception) {
            null
        }
    }

    fun reformatToLocalString(
        date: Date?,
        outputFormatter: String
    ): String {
        return try {
            SimpleDateFormat(outputFormatter, Locale.US).format(date)
            val outFormatter = SimpleDateFormat(outputFormatter, Locale.US)
            outFormatter.format(date)
        } catch (e: Exception) {
            ""
        }
    }

    fun stringToDateLeanPlum(dateStr: String): Date? {
        var d: Date? = null
        val formatter = SimpleDateFormat(LEAN_PLUM_FORMAT, Locale.US)
        formatter.timeZone = GMT
        try {
            formatter.isLenient = false
            d = formatter.parse(dateStr)
        } catch (e: Exception) {
            d = null
        }

        return d
    }

    fun convertTopUpDate(creationDate: String?): String? {
        return try {
            val parser = SimpleDateFormat("MMyy")
            parser.timeZone = TimeZone.getTimeZone("UTC")
            val convertedDate = parser.parse(creationDate)
            val pattern = "MM/yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            simpleDateFormat.format(convertedDate)
        } catch (ex: Exception) {
            ""
        }

    }

    fun isDatePassed(creationDate: String, parser: SimpleDateFormat): Boolean {
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val convertedDate = parser.parse(creationDate)
        return isDatePassed(convertedDate)
    }

    fun getCurrentDateWithFormat(formal: String): String {
        val sdf = SimpleDateFormat(formal, Locale.US)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(Date())
    }

    fun convertServerDateToLocalDate(serverDate: String): Date? {
        return try {
            val serverSdf = SimpleDateFormat(SERVER_DATE_FORMAT, Locale.US)
            serverSdf.timeZone = TimeZone.getTimeZone("UTC")
            val serverDate = serverSdf.parse(serverDate)

            val localSdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            localSdf.timeZone = TimeZone.getDefault()
            val localDate = localSdf.format(serverDate)

            val convertedSdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            convertedSdf.parse(localDate)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getStartAndEndOfMonthAndDay(
        calendar: Calendar,
        format: String = FORMATE_MONTH_DAY
    ): String {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH))
        val startDay = dateToString(calendar.time, format)
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endDay = dateToString(calendar.time, format)
        return "${startDay.replace("0", "")} - $endDay"
    }

    fun dayDiff(date1: Date, date2: Date) = (date2.time - date1.time) / 86400000
    fun dayDiffFromCurrent(date: Date): Long {
        return (date.time - Calendar.getInstance().time.time) / 86400000
    }

}