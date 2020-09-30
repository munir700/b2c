package co.yap.yapcore.helpers.spannables.SpannableString

import android.os.Build
import android.text.style.LineBackgroundSpan
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.Q)
class LineBackgroundSpanBuilder(private val color: Int):
    SpanBuilder {
    override fun build(): Any {
        return LineBackgroundSpan.Standard(color)
    }
}