package co.yap.yapcore.helpers.spannables.kpan

import android.text.TextPaint
import android.text.style.ClickableSpan

abstract class SimpleClickableSpan : ClickableSpan() {
    override fun updateDrawState(ds: TextPaint) {
        // no-op
    }
}