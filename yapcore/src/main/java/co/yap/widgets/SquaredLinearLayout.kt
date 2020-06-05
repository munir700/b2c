package co.yap.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import co.yap.yapcore.R

class SquaredLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val useHeight: Boolean

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.squaredImage, defStyleAttr, 0)
        useHeight = a.getBoolean(R.styleable.squaredImage_useHeight, false)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = measuredWidth
        val h = measuredHeight
        if (useHeight) {
            setMeasuredDimension(h, h)
        } else {
            setMeasuredDimension(w, w)
        }


    }
}
