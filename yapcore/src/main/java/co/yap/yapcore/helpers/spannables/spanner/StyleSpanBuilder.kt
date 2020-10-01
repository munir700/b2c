package co.yap.yapcore.helpers.spannables.spanner

import android.text.style.StyleSpan

internal class StyleSpanBuilder(private val style: Int) :
    SpanBuilder {
    override fun build(): Any = StyleSpan(style)
}
