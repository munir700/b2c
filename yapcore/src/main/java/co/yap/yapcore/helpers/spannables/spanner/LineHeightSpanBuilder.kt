package co.yap.yapcore.helpers.spannables.spanner

import android.os.Build
import android.text.style.LineHeightSpan
import androidx.annotation.RequiresApi

@RequiresApi(api = Build.VERSION_CODES.Q)
class LineHeightSpanBuilder(private val height: Int):
    SpanBuilder {
    override fun build(): Any {
        return LineHeightSpan.Standard(height)
    }
}