package co.yap.widgets.edittext

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.ViewCompat
import co.yap.widgets.edittext.DrawableEnriched.Companion.UNDEFINED
import co.yap.yapcore.R
import kotlin.math.roundToInt

@Keep
class RichDrawableHelper(
    private val mContext: Context,
    attrs: AttributeSet,
    defStyleAttr: Int,
    defStyleRes: Int
) : DrawableEnriched {
    private var mDrawableWidth: Int = 0
    private var mDrawableHeight: Int = 0
    private var mDrawableStartVectorId: Int = 0
    private var mDrawableTopVectorId: Int = 0
    private var mDrawableEndVectorId: Int = 0
    private var mDrawableBottomVectorId: Int = 0
    var disableEmoji: Boolean = false
    var disableSpaces: Boolean = false

    @ColorInt
    private var mDrawableTint: Int = 0

    init {
        val array = mContext.obtainStyledAttributes(
            attrs,
            R.styleable.TextViewRichDrawable,
            defStyleAttr,
            defStyleRes
        )
        try {
            mDrawableWidth = array.getDimensionPixelSize(
                R.styleable.TextViewRichDrawable_compoundDrawableWidth,
                UNDEFINED
            )
            mDrawableHeight = array.getDimensionPixelSize(
                R.styleable.TextViewRichDrawable_compoundDrawableHeight,
                UNDEFINED
            )
            mDrawableStartVectorId =
                array.getResourceId(R.styleable.TextViewRichDrawable_drawableStartVector, UNDEFINED)
            mDrawableTopVectorId =
                array.getResourceId(R.styleable.TextViewRichDrawable_drawableTopVector, UNDEFINED)
            mDrawableEndVectorId =
                array.getResourceId(R.styleable.TextViewRichDrawable_drawableEndVector, UNDEFINED)
            mDrawableBottomVectorId =
                array.getResourceId(
                    R.styleable.TextViewRichDrawable_drawableBottomVector,
                    UNDEFINED
                )
            mDrawableTint =
                array.getColor(R.styleable.TextViewRichDrawable_rtdrawableTint, UNDEFINED)
            disableEmoji = array.getBoolean(
                R.styleable.TextViewRichDrawable_rtdrawableDisableEmoji,
                false
            )
            disableSpaces = array.getBoolean(
                R.styleable.TextViewRichDrawable_disableSpaces,
                false
            )
        } finally {
            array.recycle()
        }
    }

    fun apply(textView: TextView) {
        if (mDrawableWidth > 0 || mDrawableHeight > 0 || mDrawableStartVectorId > 0 || mDrawableTopVectorId > 0
            || mDrawableEndVectorId > 0 || mDrawableBottomVectorId > 0
        ) {
            initCompoundDrawables(
                textView, mDrawableStartVectorId, mDrawableTopVectorId,
                mDrawableEndVectorId, mDrawableBottomVectorId
            )
        }
    }

    private fun initCompoundDrawables(
        textView: TextView, drawableStartVectorId: Int, drawableTopVectorId: Int,
        drawableEndVectorId: Int, drawableBottomVectorId: Int
    ) {
        val drawables = textView.compoundDrawables

        inflateVectors(
            textView, drawableStartVectorId, drawableTopVectorId, drawableEndVectorId,
            drawableBottomVectorId, drawables
        )
        scale(drawables)
        tint(drawables)

        textView.setCompoundDrawables(
            drawables[LEFT_DRAWABLE_INDEX], drawables[TOP_DRAWABLE_INDEX],
            drawables[RIGHT_DRAWABLE_INDEX], drawables[BOTTOM_DRAWABLE_INDEX]
        )
    }

    private fun inflateVectors(
        textView: TextView, drawableStartVectorId: Int, drawableTopVectorId: Int,
        drawableEndVectorId: Int, drawableBottomVectorId: Int, drawables: Array<Drawable>
    ) {
        val rtl = ViewCompat.getLayoutDirection(textView) == ViewCompat.LAYOUT_DIRECTION_RTL

        if (drawableStartVectorId != UNDEFINED) {
            drawables[if (rtl) RIGHT_DRAWABLE_INDEX else LEFT_DRAWABLE_INDEX] =
                getVectorDrawable(drawableStartVectorId)!!
        }
        if (drawableTopVectorId != UNDEFINED) {
            drawables[TOP_DRAWABLE_INDEX] = getVectorDrawable(drawableTopVectorId)!!
        }
        if (drawableEndVectorId != UNDEFINED) {
            drawables[if (rtl) LEFT_DRAWABLE_INDEX else RIGHT_DRAWABLE_INDEX] =
                getVectorDrawable(drawableEndVectorId)!!
        }
        if (drawableBottomVectorId != UNDEFINED) {
            drawables[BOTTOM_DRAWABLE_INDEX] = getVectorDrawable(drawableBottomVectorId)!!
        }
    }

    private fun scale(drawables: Array<Drawable>) {
        if (mDrawableHeight > 0 || mDrawableWidth > 0) {
            for (drawable in drawables) {
                if (drawable == null) {
                    continue
                }
                val realBounds = Rect(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
                var actualDrawableWidth = realBounds.width().toFloat()
                var actualDrawableHeight = realBounds.height().toFloat()
                val actualDrawableRatio = actualDrawableHeight / actualDrawableWidth
                val scale: Float
                // check if both width and height defined then adjust drawable size according to the ratio
                if (mDrawableHeight > 0 && mDrawableWidth > 0) {
                    val placeholderRatio = mDrawableHeight / mDrawableWidth.toFloat()
                    if (placeholderRatio > actualDrawableRatio) {
                        scale = mDrawableWidth / actualDrawableWidth
                    } else {
                        scale = mDrawableHeight / actualDrawableHeight
                    }
                } else if (mDrawableHeight > 0) { // only height defined
                    scale = mDrawableHeight / actualDrawableHeight
                } else { // only width defined
                    scale = mDrawableWidth / actualDrawableWidth
                }
                actualDrawableWidth *= scale
                actualDrawableHeight *= scale
                realBounds.right = realBounds.left + actualDrawableWidth.roundToInt()
                realBounds.bottom = realBounds.top + actualDrawableHeight.roundToInt()
                drawable.bounds = realBounds
            }
        } else {
            drawables.forEach { d ->
                if (d != null)
                    d.bounds = Rect(0, 0, d.intrinsicWidth, d.intrinsicHeight)
            }
        }
    }

    private fun tint(drawables: Array<Drawable>) {
        if (mDrawableTint != UNDEFINED) {
            for (i in drawables.indices) {
                if (drawables[i] == null) {
                    continue
                }

                val tintedDrawable = DrawableCompat.wrap(drawables[i])
                DrawableCompat.setTint(tintedDrawable.mutate(), mDrawableTint)

                drawables[i] = tintedDrawable
            }
        }
    }

    private fun getVectorDrawable(@DrawableRes resId: Int): Drawable? {
        return ResourcesCompat.getDrawable(mContext.resources, resId, mContext.theme)
    }

    /**
     * {@inheritDoc}
     */
    override fun getCompoundDrawableHeight(): Int {
        return mDrawableHeight
    }

    /**
     * {@inheritDoc}
     */
    override fun getCompoundDrawableWidth(): Int {
        return mDrawableWidth
    }

    override fun setDrawableStartVectorId(@DrawableRes id: Int) {
        mDrawableStartVectorId = id
    }

    override fun setDrawableEndVectorId(@DrawableRes id: Int) {
        mDrawableEndVectorId = id
    }

    override fun setDrawableTopVectorId(@DrawableRes id: Int) {
        mDrawableTopVectorId = id
    }

    override fun setDrawableBottomVectorId(@DrawableRes id: Int) {
        mDrawableBottomVectorId = id
    }

    companion object {
        private const val LEFT_DRAWABLE_INDEX = 0
        private const val TOP_DRAWABLE_INDEX = 1
        private const val RIGHT_DRAWABLE_INDEX = 2
        private const val BOTTOM_DRAWABLE_INDEX = 3
    }
}
