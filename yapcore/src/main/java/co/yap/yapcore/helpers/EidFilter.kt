package co.yap.yapcore.helpers

import android.text.InputFilter
import android.text.Spanned
import java.lang.StringBuilder

/**
 * Class will filter EID in format xxx-xxxx-xxxxxxx-x
 *
 * @author Fahad Nasrullah
 */
class EidFilter(val dashes: IntArray, val characterToReplace: Char) : InputFilter {
    val maxLength: Int = 18 // 784-1993-1715061-5
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (dest != null && dest.toString().trim { it <= ' ' }.length > maxLength) {
            return null
        }
        if (source.length == 1 && contains(dstart, dashes) && source[0] != characterToReplace) {
            return characterToReplace.toString() + source.toString()
        }

        //copy-paste case
        var spacesCount = 0
        if (start == 0 && source.length == end) {
            val sb = StringBuilder()
            for (i in source.indices) {
                val symbol = source[i]
                if (contains(i + spacesCount, dashes) && symbol != characterToReplace) {
                    spacesCount++
                    sb.append(characterToReplace)
                }
                sb.append(symbol)
            }
            return sb.toString()
        }
        //unhandled: partial copy-paste
        return null // keep original
    }

    private fun contains(i: Int, array: IntArray): Boolean {
        for (j in array) {
            if (j == i) {
                return true
            }
        }
        return false
    }
}