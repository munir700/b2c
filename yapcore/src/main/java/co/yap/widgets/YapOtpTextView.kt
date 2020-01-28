package co.yap.widgets

import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.LinearLayout
import androidx.core.view.forEach
import co.yap.yapcore.helpers.extentions.getScreenWidth

class YapOtpTextView : OtpTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)

                    forEach { view ->
                        if (view is LinearLayout && view.childCount >= 6) {
                            val layout = view as LinearLayout
                            view.forEach { v ->
                                v.layoutParams.width = getScreenWidth() / 8
                                val height = (getScreenWidth() / 8) * .25
                                v.layoutParams.height = (getScreenWidth() / 8) + height.toInt()
                                // layout.layoutParams.width =
                            }
                        }
                    }
                }
            })
    }

}