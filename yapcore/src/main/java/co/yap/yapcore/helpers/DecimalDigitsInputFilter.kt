package co.yap.yapcore.helpers

import android.text.InputFilter
import android.text.Spanned
import co.yap.app.YAPApplication
import java.util.regex.Pattern

class DecimalDigitsInputFilter(aiMinorUnits: Int?) : InputFilter {
    private var moPattern: Pattern =
        Pattern.compile("[0-9]*+((\\.[0-9]{0,${aiMinorUnits ?: YAPApplication.selectedCurrency}})?)||(\\.)?")

    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        var lsStart = ""
        var lsInsert = ""
        var lsEnd = ""
        var lsText = ""
        lsText = dest.toString()
        if (lsText.isNotEmpty()) {

            lsStart = lsText.substring(0, dstart)
            if (source !== "") {
                lsInsert = source.toString()
            }
            lsEnd = lsText.substring(dend)
            lsText = lsStart + lsInsert + lsEnd
        }

        val loMatcher = moPattern.matcher(lsText)
        return if (!loMatcher.matches()) {
            ""
        } else null

    }

}
