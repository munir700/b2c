package com.yap.uikit.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.TextUtils
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Checks if a string is a valid email
 * @return a boolean representing true if email is valid else false
 */
fun String.isEmail() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

/**
 * Checks if String is Number.
 * Checks against regex `^[0-9]+$`
 * @return a boolean representing true if all the characters are numeric else false
 */
fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}

fun Context?.string(@StringRes idRes: Int): String = Resources.getSystem().getString(idRes)

fun isWhiteSpaces(@Nullable s: String?) = s != null && s.matches("\\s+".toRegex())

fun isEmpty(@Nullable text: String?) =
    text == null || TextUtils.isEmpty(text) || isWhiteSpaces(text) || text.equals(null)

fun isEmpty(@Nullable text: Any?) = text == null || isEmpty(text.toString())

fun isEmpty(@Nullable text: EditText?) = text == null || isEmpty(text.text.toString())

fun isEmpty(@Nullable text: TextView?) = text == null || isEmpty(text.text.toString())

fun isEmpty(@Nullable txt: TextInputLayout?) = txt == null || isEmpty(txt!!.getEditText())

fun toString(@NonNull editText: EditText) = editText.text.toString()

@SuppressLint("DefaultLocale")
fun String.toCamelCase(): String = split(" ").joinToString(" ") { it.toLowerCase().capitalize() }

fun String.getQRCode(): String {
    if ((this.contains("yap-app:"))) {
        return this.replace("yap-app:", "")
    }
    return this
}

fun String.generateQRCode(): String {
    return "yap-app:$this"
}

fun String?.encodeToUTF8(): String {
    this?.let {
        return URLEncoder.encode(it, StandardCharsets.UTF_8.name())
    } ?: return ""
}

fun String?.decodeToUTF8(): String {
    this?.let {
        return URLDecoder.decode(it, StandardCharsets.UTF_8.name())
    } ?: return ""

}

fun String?.getOtpFromMessage(): String {
    var otpCode = ""
    this?.let {
        val pattern: Pattern = Pattern.compile("(|^)\\d{6}")
        val matcher: Matcher = pattern.matcher(it)
        if (matcher.find()) {
            otpCode = matcher.group(0) ?: ""
        }
    }
    return otpCode
}

fun String?.getValueWithoutComa(): String {
    var string = this
    if (string?.contains(",") == true) {
        string = string.replace(",", "")
    }
    if (string?.contains(" ") == true) {
        string = string.substring(string.indexOf(" ") + 1, string.length)
    }
    return string ?: ""
}

/**
 * Checks if a string contains numbers in increasing or decreasing sequence
 */
fun String.isSequenced(): Boolean {
    val sequenced = this.run {
        val first = if (length > 0) get(0).toString().toIntOrNull() else null
        first?.let {
            val low = first - (length - 1)
            val high = first + (length - 1)

            val lowSeq = (low..first).asReversedString()
            val highSeq = (first..high).asString()

            lowSeq == this || highSeq == this
        }
    }
    return sequenced ?: false
}


private fun IntRange.asString(): String = run {
    val s = StringBuilder()
    forEach { s.append(it.toString()) }
    s.toString()
}

private fun IntRange.asReversedString(): String = run {
    asString().reversed()
}

/**
 * Checks if a string contains all same chars like "0000"
 */
fun String.hasAllSameChars(): Boolean = this.run {
    this.matches("^([0-9])\\1*".toRegex())
}