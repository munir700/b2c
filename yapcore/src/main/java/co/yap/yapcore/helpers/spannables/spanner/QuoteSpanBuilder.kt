package co.yap.yapcore.helpers.spannables.spanner

import android.text.style.QuoteSpan
import androidx.annotation.ColorInt

internal class QuoteSpanBuilder(@ColorInt private val color: Int?) :
    SpanBuilder {
    override fun build(): Any {
        return if (color == null) {
            QuoteSpan()
        } else {
            QuoteSpan(color)
        }
    }
}
