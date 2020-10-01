package co.yap.yapcore.helpers.spannables

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.*
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


fun spannable(func: () -> SpannableString) = func()
private fun span(s: CharSequence, o: Any) =
    (if (s is String) SpannableString(s) else s as? SpannableString
        ?: SpannableString("")).apply { setSpan(o, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }

operator fun SpannableString.plus(s: SpannableString) = SpannableString(TextUtils.concat(this, s))
operator fun SpannableString.plus(s: String?) = SpannableString(TextUtils.concat(this, s ?: ""))

fun bold(s: CharSequence) = span(
    s,
    StyleSpan(Typeface.BOLD)
)

fun bold(s: SpannableString) = span(
    s,
    StyleSpan(Typeface.BOLD)
)

fun italic(s: CharSequence) = span(
    s,
    StyleSpan(Typeface.ITALIC)
)

fun italic(s: SpannableString) =
    span(s, StyleSpan(Typeface.ITALIC))

fun underline(s: CharSequence) =
    span(s, UnderlineSpan())

fun underline(s: SpannableString) =
    span(s, UnderlineSpan())

fun strike(s: CharSequence) =
    span(s, StrikethroughSpan())

fun strike(s: SpannableString) =
    span(s, StrikethroughSpan())

fun sup(s: CharSequence) =
    span(s, SuperscriptSpan())

fun sup(s: SpannableString) =
    span(s, SuperscriptSpan())

fun sub(s: CharSequence) =
    span(s, SubscriptSpan())

fun sub(s: SpannableString) =
    span(s, SubscriptSpan())

fun size(size: Float, s: CharSequence) =
    span(s, RelativeSizeSpan(size))

fun size(size: Float, s: SpannableString) =
    span(s, RelativeSizeSpan(size))

fun color(color: Int, s: CharSequence) =
    span(s, ForegroundColorSpan(color))


fun Context.color(@ColorRes id: Int, s: CharSequence) =
    span(s, ForegroundColorSpan(ContextCompat.getColor(this, id)))

fun Context.color(@ColorRes id: Int, s: SpannableString) =
    span(s, ForegroundColorSpan(ContextCompat.getColor(this, id)))

fun color(color: Int, s: SpannableString) =
    span(s, ForegroundColorSpan(color))

fun background(color: Int, s: CharSequence) =
    span(s, BackgroundColorSpan(color))

fun background(color: Int, s: SpannableString) =
    span(s, BackgroundColorSpan(color))

fun url(url: String, s: CharSequence) =
    span(s, URLSpan(url))

fun url(url: String, s: SpannableString) =
    span(s, URLSpan(url))

fun normal(s: CharSequence) =
    span(s, SpannableString(s))

fun normal(s: SpannableString) =
    span(s, SpannableString(s))

/**
 * Helps to set clickable part in text.
 *
 * Don't forget to set android:textColorLink="@color/link" (click selector) and
 * android:textColorHighlight="@color/window_background" (background color while clicks)
 * in the TextView where you will use this.
 */
fun SpannableString.withClickableSpan(
    clickablePart: String,
    onClickListener: () -> Unit
): SpannableString {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) = onClickListener.invoke()
    }
    val clickablePartStart = indexOf(clickablePart)
    setSpan(
        clickableSpan,
        clickablePartStart,
        clickablePartStart + clickablePart.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}