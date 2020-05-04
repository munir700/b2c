package co.yap.yapcore.helpers.extentions

fun String?.parseToDouble() = try {
    this?.toDouble()?:0.0
} catch (e: NumberFormatException) {
    0.0
}

fun String.parseToInt() = try {
    toInt()
} catch (e: NumberFormatException) {
    0
}
fun String.parseToLong() = try {
    toLong()
} catch (e: NumberFormatException) {
    0L
}

fun Double.parseToInt() = try {
    toInt()
} catch (e: NumberFormatException) {
    0
}

fun String.parseToFloat() = try {
    toFloat()
} catch (e: NumberFormatException) {
    0.0F
}

fun CharSequence?.parseToDouble() = try {
    this?.toString()?.replace(",","")?.toDouble()?:0.0
} catch (e: NumberFormatException) {
    0.0
}
