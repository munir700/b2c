package co.yap.widgets.edittext

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.abs

@Keep
open class   EditTextRichDrawable : TextInputEditText, DrawableEnriched {

    private var mRichDrawableHelper: RichDrawableHelper? = null
    private var onDrawableClickListener: OnDrawableClickListener? = null
    private var defaultClickListener: OnDrawableClickListener? = null
    private var positionX: Int = 0
    private var positionY: Int = 0
    private var drawableRight: Drawable? = null
    private var drawableLeft: Drawable? = null
    private var drawableTop: Drawable? = null
    private var drawableBottom: Drawable? = null

    private val defaultClickListenerAdapter: OnDrawableClickListener =
        object : OnDrawableClickListener {
            override fun onClick(view : View, target: DrawablePosition) {
                when (target) {
                    DrawablePosition.BOTTOM -> {

                    }
                    DrawablePosition.LEFT -> {

                    }
                    DrawablePosition.TOP -> {

                    }
                    DrawablePosition.RIGHT -> {

                    }
                }
            }
        }

    constructor(context: Context) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs, defStyleAttr, 0)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        mRichDrawableHelper = RichDrawableHelper(context, attrs!!, defStyleAttr, defStyleRes)
        mRichDrawableHelper?.apply(this)
        defaultClickListener = defaultClickListenerAdapter
    }

    /**
     * {@inheritDoc}
     */
    override fun getCompoundDrawableHeight(): Int {
        return mRichDrawableHelper?.getCompoundDrawableHeight() ?: 0
    }

    /**
     * {@inheritDoc}
     */
    override fun getCompoundDrawableWidth(): Int {
        return mRichDrawableHelper?.getCompoundDrawableWidth() ?: 0
    }

    override fun setDrawableStartVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableStartVectorId(id)
        mRichDrawableHelper?.apply(this)
    }

    override fun setDrawableEndVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableEndVectorId(id)
        mRichDrawableHelper?.apply(this)
    }

    override fun setDrawableTopVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableTopVectorId(id)
        mRichDrawableHelper?.apply(this)
    }

    override fun setDrawableBottomVectorId(@DrawableRes id: Int) {
        mRichDrawableHelper?.setDrawableBottomVectorId(id)
        mRichDrawableHelper?.apply(this)
    }

    override fun setCompoundDrawables(
        leftDrawable: Drawable?,
        topDrawable: Drawable?,
        rightDrawable: Drawable?,
        bottomDrawable: Drawable?
    ) {
        leftDrawable?.let { drawableLeft = it }
        rightDrawable?.let { drawableRight = it }
        topDrawable?.let { drawableTop = it }
        bottomDrawable?.let { drawableBottom = it }
        super.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable)
    }

    fun setDrawableClickListener(OnDrawableClickListener: OnDrawableClickListener) {
        this.onDrawableClickListener = OnDrawableClickListener
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var bounds: Rect?
//        val editText = this
//        this.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(char: CharSequence, p1: Int, p2: Int, p3: Int) {
//                if (char.isEmpty()) {
//                    if (!isDrawableShownWhenTextIsEmpty) editText.setCompoundDrawablesWithIntrinsicBounds(
//                        0,
//                        0,
//                        0,
//                        0
//                    )
//                } else editText.setCompoundDrawables(
//                    drawableLeft,
//                    drawableTop,
//                    drawableRight,
//                    drawableBottom
//                )
//            }
//
//
//        })
        if (event.action == MotionEvent.ACTION_DOWN) {
            positionX = event.x.toInt()
            positionY = event.y.toInt()

            // this works for left since container shares 0,0 origin with bounds

            drawableLeft?.let {
                bounds = it.bounds
                setupDrawableLeftClick(bounds, event)
            }
            drawableRight?.let {
                bounds = it.bounds
                setupDrawableRightClick(bounds, event)
            }
            drawableTop?.let {
                bounds = it.bounds
                setupDrawableTopClick(bounds, event)
            }
            drawableBottom?.let {
                bounds = it.bounds
                setupDrawableBottomClick(bounds, event)
            }

        }
        return super.onTouchEvent(event)
    }

    private fun setupDrawableBottomClick(bounds: Rect?, event: MotionEvent) {
        val extraClickingArea = 13
        if (abs((width - paddingLeft - paddingRight) / 2 + paddingLeft - positionX) <= bounds!!.width() / 2 + extraClickingArea) {
            onDrawableClickListener?.onClick(this, DrawablePosition.BOTTOM)
            defaultClickListener?.onClick(this, DrawablePosition.BOTTOM)
            event.action = MotionEvent.ACTION_CANCEL
        }
    }

    private fun setupDrawableTopClick(bounds: Rect?, event: MotionEvent) {
        val extraClickingArea = 13
        if (abs((width - paddingLeft - paddingRight) / 2 + paddingLeft - positionX) <= bounds!!.width() / 2 + extraClickingArea) {
            onDrawableClickListener?.onClick(this, DrawablePosition.TOP)
            defaultClickListener?.onClick(this, DrawablePosition.TOP)
            event.action = MotionEvent.ACTION_CANCEL
        }
    }

    private fun setupDrawableLeftClick(bounds: Rect?, event: MotionEvent) {
        var xClickPosition: Int
        var yClickPosition: Int
        /*
         * @return pixels into dp
         */
        val extraClickArea = (13 * resources.displayMetrics.density + 0.5).toInt()

        xClickPosition = positionX
        yClickPosition = positionY

        if (!bounds!!.contains(positionX, positionY)) {
            /** Gives some extra space for tapping.  */
            xClickPosition = positionX - extraClickArea
            yClickPosition = positionY - extraClickArea

            if (xClickPosition <= 0) xClickPosition = positionX
            if (yClickPosition <= 0) yClickPosition = positionY

            /** Creates square from the smallest value  from x or y*/
            if (xClickPosition < yClickPosition) yClickPosition = xClickPosition
        }

        if (bounds.contains(xClickPosition, yClickPosition) && onDrawableClickListener != null) {
            onDrawableClickListener?.onClick(this, DrawablePosition.LEFT)
            defaultClickListener?.onClick(this, DrawablePosition.LEFT)
            event.action = MotionEvent.ACTION_CANCEL

        }
    }

    private fun setupDrawableRightClick(bounds: Rect?, event: MotionEvent) {
        var xClickPosition: Int
        var yClickPosition: Int
        val extraClickingArea = 18

        xClickPosition = positionX + extraClickingArea
        yClickPosition = positionY - extraClickingArea
        /**
         * It right drawable -> subtract the value of x from the width of view. so that width - tapped area                     * will result in x co-ordinate in drawable bound.
         */
        xClickPosition = width - xClickPosition
        if (xClickPosition <= 0) xClickPosition += extraClickingArea
        /* If after calculating for extra clickable area is negative.
         * assign the original value so that after subtracting
         * extra clicking area value doesn't go into negative value.
         */
        if (yClickPosition <= 0) yClickPosition = positionY
        /**If drawable bounds contains the x and y points then move ahead. */
        if (bounds!!.contains(
                xClickPosition,
                yClickPosition
            ) && (onDrawableClickListener != null || defaultClickListener != null)
        ) {
            onDrawableClickListener?.onClick(this, DrawablePosition.RIGHT)
            defaultClickListener?.onClick(this, DrawablePosition.RIGHT)
            event.action = MotionEvent.ACTION_CANCEL
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["app:onDrawableClickListener"], requireAll = false)
        fun setOnDrawableClickListener(
            view: EditTextRichDrawable,
            listener: OnDrawableClickListener?
        ) {
            listener?.let { view.setDrawableClickListener(listener) }
        }

    }
}
