package co.yap.yapcore.helpers

import org.json.JSONArray
import java.util.regex.Pattern

object StringUtils {
    fun validateName(text: String): Boolean {
        val expression = "^[a-zA-Z'. ]*$"
        val pattern = Pattern.compile(expression)
        return pattern.matcher(text).matches()
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
}