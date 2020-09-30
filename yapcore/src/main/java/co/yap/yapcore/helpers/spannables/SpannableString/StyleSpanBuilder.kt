package co.yap.yapcore.helpers.spannables.SpannableString

import android.text.style.StyleSpan
import co.yap.yapcore.helpers.spannables.SpannableString.SpanBuilder

internal class StyleSpanBuilder(private val style: Int) :
    SpanBuilder {
    override fun build(): Any = StyleSpan(style)
}
