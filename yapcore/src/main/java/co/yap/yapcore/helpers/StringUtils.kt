package co.yap.yapcore.helpers

import org.json.JSONArray
import java.lang.StringBuilder
import java.util.regex.Pattern

object StringUtils {


    fun validateName(name: String): Boolean {

        var inputStr: CharSequence = ""
        var isValid = false
        val expression = "^[a-zA-Z'. ]*$"
        inputStr = name
        val pattern = Pattern.compile(expression)
        val matcher = pattern.matcher(inputStr)

        if (matcher.matches() && !name.isNullOrEmpty()) {
            if (name.length >= 2) {
                isValid = true
            }
        }
        return isValid
    }

    /**
     * Function takes a string value like "["hello", "world", "1"]"
     * and evaluates it to return an Array<String?>
     */

    fun eval(text: String?): Array<String?> {
        val jsonArray = JSONArray(text)
        val strArr = arrayOfNulls<String>(jsonArray.length())

        for (i in 0 until jsonArray.length()) {
            strArr[i] = jsonArray.getString(i)
        }
        return strArr
    }

    /**
     * Function takes a string value like "["hello", "world", "1"]"
     * and evaluates it to return an Array<String?>
     */

    fun toStringArray(text: String?): Array<String> = eval(text).requireNoNulls()

    /**
     * Checks if a string contains numbers in increasing or decreasing sequence
     */
    fun isSequenced(text: String): Boolean {
        val sequenced = text.run {
            val first = get(0).toString().toIntOrNull()
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
    fun hasAllSameChars(text: String): Boolean = text.run {
        val first = get(0).toString()
        replace(first, "").isEmpty()
    }


}