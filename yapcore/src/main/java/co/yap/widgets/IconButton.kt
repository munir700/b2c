package co.yap.widgets


import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.Button

class IconButton : Button {
    protected var drawableWidth: Int = 0
    protected lateinit var drawablePosition: DrawablePositions
    protected var iconsPadding: Int = 0

    // Cached to prevent allocation during onLayout
    internal var bounds: Rect

    enum class DrawablePositions {
        NONE,
        LEFT,
        RIGHT
    }

    constructor(context: Context) : super(context) {
        bounds = Rect()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        bounds = Rect()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        bounds = Rect()
    }

    fun setIconPadding(padding: Int) {
        iconsPadding = padding
        requestLayout()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val textPaint = paint
        val text = text.toString()
        textPaint.getTextBounds(text, 0, text.length, bounds)

        val textWidth = bounds.width()
        val contentWidth = drawableWidth + iconsPadding + textWidth

        val contentLeft = (width / 2.0 - contentWidth / 2.0).toInt()
        compoundDrawablePadding = -contentLeft + iconsPadding
        when (drawablePosition) {
            IconButton.DrawablePositions.LEFT -> setPadding(contentLeft, 0, 0, 0)
            IconButton.DrawablePositions.RIGHT -> setPadding(0, 0, contentLeft, 0)
            else -> setPadding(0, 0, 0, 0)
        }
    }

    override fun setCompoundDrawablesWithIntrinsicBounds(
        left: Drawable?,
        top: Drawable?,
        right: Drawable?,
        bottom: Drawable?
    ) {
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom)
        if (null != left) {
            drawableWidth = left.intrinsicWidth
            drawablePosition = DrawablePositions.LEFT
        } else if (null != right) {
            drawableWidth = right.intrinsicWidth
            drawablePosition = DrawablePositions.RIGHT
        } else {
            drawablePosition = DrawablePositions.NONE
        }
        requestLayout()
    }
}


