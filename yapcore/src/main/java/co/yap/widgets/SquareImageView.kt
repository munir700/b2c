package co.yap.widgets

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import co.yap.yapcore.R

class SquareImageView : AppCompatImageView {
    var hwRatio = 1f
        private set

    constructor(context: Context) : super(context) {
        setAttributes(context, null, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        setAttributes(context, attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
        setAttributes(context, attrs, defStyle)
    }

    private fun setAttributes(
        context: Context,
        attrs: AttributeSet?, defStyleAttr: Int
    ) {
        attrs?.let {
            val ta =
                context.obtainStyledAttributes(attrs, R.styleable.squaredImage, defStyleAttr, 0)
            hwRatio = ta.getFloat(R.styleable.squaredImage_hwRatio, 1f)
            ta.recycle()

        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        val calculatedHeight = calculateHeightByRatio(width)
        if (calculatedHeight != height) {
            setMeasuredDimension(width, calculatedHeight)
        }
    }

    private fun calculateHeightByRatio(side: Int): Int {
        return (hwRatio * side.toFloat()).toInt()
    }

    fun setXyRatio(xyRatio: Float) {
        hwRatio = xyRatio
        this.invalidate()
    }
}