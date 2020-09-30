package co.yap.yapcore.helpers.spannables.SpannableString

open class Span internal constructor(private val builder: SpanBuilder) {
    internal fun buildSpan() = builder.build()
}
