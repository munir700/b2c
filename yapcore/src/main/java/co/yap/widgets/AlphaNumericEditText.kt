package co.yap.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.annotation.Keep
import androidx.appcompat.widget.AppCompatEditText
import co.yap.widgets.popupwindow.Popup
import co.yap.widgets.popupwindow.RelativePopupWindow.HorizontalPosition
import co.yap.widgets.popupwindow.RelativePopupWindow.VerticalPosition
import co.yap.yapcore.R
import kotlin.math.abs

class AlphaNumericEditText(context: Context, attrs: AttributeSet) :
    AppCompatEditText(context, attrs) {

    private var backupString = ""
    private var drawableRight: Drawable? = null
    private var drawableLeft: Drawable? = null
    private var drawableTop: Drawable? = null
    private var drawableBottom: Drawable? = null
    private var positionX: Int = 0
    private var positionY: Int = 0
    private var isDrawableShownWhenTextIsEmpty = true
    private var onDrawableClickListener: OnDrawableClickListener? = null
    private var defaultClickListener: OnDrawableClickListener? = null
    var popupTextValue: String? = ""
    private var disableEmoji: Boolean = false
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable) {

        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            this@AlphaNumericEditText.removeTextChangedListener(this)
            var orignalString = charSequence.toString()
            if (orignalString.isNotEmpty() && Character.isWhitespace(charSequence[0])) {
                if (text?.length ?: 0 > 1) {
                    setText(backupString)
                } else
                    setText("")
            } else {
                backupString = orignalString
            }

            this@AlphaNumericEditText.addTextChangedListener(this)
        }
    }

    private val defaultClickListenerAdapter: OnDrawableClickListener =
        object : OnDrawableClickListener {
            override fun onClick(target: DrawablePosition) {
                when (target) {
                    DrawablePosition.BOTTOM -> {

                    }
                    DrawablePosition.LEFT -> {

                    }
                    DrawablePosition.TOP -> {

                    }
                    DrawablePosition.RIGHT -> {
                        if (popupTextValue?.isNotEmpty() == true) {
                            val popup = Popup(context, popupTextValue ?: "")
                            popup.width = ViewGroup.LayoutParams.WRAP_CONTENT
                            popup.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            popup.showOnAnchor(
                                anchor = this@AlphaNumericEditText,
                                vertPos = VerticalPosition.ABOVE,
                                horizPos = HorizontalPosition.CENTER,
                                x = 100,
                                y = 0,
                                fitInScreen = true
                            )
                        }
                    }
                }
            }
        }

    init {
        addTextChangedListener((textWatcher))
        parseAttributes(
            context.obtainStyledAttributes(
                attrs,
                R.styleable.AlphaNumericEditText
            )
        )
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        updateValue(text.toString())
    }

    private fun updateValue(text: String) {
        setText(text)
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        removeEndingSpaces()
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            removeEndingSpaces()
        }
        return super.onKeyPreIme(keyCode, event)
    }

    override fun onEditorAction(actionCode: Int) {
        if (actionCode == EditorInfo.IME_ACTION_NEXT
            || actionCode == EditorInfo.IME_ACTION_DONE
        ) {
            removeEndingSpaces()
        }
        super.onEditorAction(actionCode)
    }

    private fun removeEndingSpaces() {
        removeTextChangedListener(textWatcher)
        setText(text.toString().trim())
        setSelection(text?.length ?: 0)
        addTextChangedListener(textWatcher)
    }

    private fun parseAttributes(obtainStyledAttributes: TypedArray) {
        isDrawableShownWhenTextIsEmpty = obtainStyledAttributes.getBoolean(
            R.styleable.AlphaNumericEditText_showDrawableWhenTextIsEmpty,
            isDrawableShownWhenTextIsEmpty
        )
        disableEmoji = obtainStyledAttributes.getBoolean(
            R.styleable.AlphaNumericEditText_disableEmoji,
            false
        )
        popupTextValue = resources.getText(
            obtainStyledAttributes
                .getResourceId(
                    R.styleable.AlphaNumericEditText_popupContentText,
                    R.string.empty_string
                )
        ).toString()
        obtainStyledAttributes.recycle()
        hasDrawable(isDrawableShownWhenTextIsEmpty)
        defaultClickListener = defaultClickListenerAdapter
        if(disableEmoji) filters = arrayOf<InputFilter>(EmojiExcludeFilter())
    }

    fun hasDrawable(value: Boolean) {
        isDrawableShownWhenTextIsEmpty = value
        if (!isDrawableShownWhenTextIsEmpty) {
            this.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
        invalidate()
    }


    override fun setCompoundDrawables(
        leftDrawable: Drawable?,
        topDrawable: Drawable?,
        rightDrawable: Drawable?,
        bottomDrawable: Drawable?
    ) {
        if (leftDrawable != null) drawableLeft = leftDrawable
        if (rightDrawable != null) drawableRight = rightDrawable
        if (topDrawable != null) drawableTop = topDrawable
        if (bottomDrawable != null) drawableBottom = bottomDrawable
        super.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var bounds: Rect?
        val editText = this
        if (event.action == MotionEvent.ACTION_DOWN) {
            positionX = event.x.toInt()
            positionY = event.y.toInt()

            // this works for left since container shares 0,0 origin with bounds
            if (drawableLeft != null) {
                bounds = drawableLeft?.bounds
                setupDrawableLeftClick(bounds, event)
            }

            if (drawableRight != null) {
                bounds = drawableRight?.bounds
                setupDrawableRightClick(bounds, event)
            }

            if (drawableTop != null) {
                bounds = drawableTop?.bounds
                setupDrawableTopClick(bounds, event)
            }

            if (drawableBottom != null) {
                bounds = drawableBottom?.bounds
                setupDrawableBottomClick(bounds, event)
            }


        }
        return super.onTouchEvent(event)
    }

    private fun setupDrawableBottomClick(bounds: Rect?, event: MotionEvent) {
        val extraClickingArea = 13
        if (abs((width - paddingLeft - paddingRight) / 2 + paddingLeft - positionX) <= bounds!!.width() / 2 + extraClickingArea) {
            onDrawableClickListener?.onClick(DrawablePosition.BOTTOM)
            defaultClickListener?.onClick(DrawablePosition.BOTTOM)
            event.action = MotionEvent.ACTION_CANCEL
        }
    }

    private fun setupDrawableTopClick(bounds: Rect?, event: MotionEvent) {
        val extraClickingArea = 13
        if (abs((width - paddingLeft - paddingRight) / 2 + paddingLeft - positionX) <= bounds!!.width() / 2 + extraClickingArea) {
            onDrawableClickListener?.onClick(DrawablePosition.TOP)
            defaultClickListener?.onClick(DrawablePosition.TOP)
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
            onDrawableClickListener?.onClick(DrawablePosition.LEFT)
            defaultClickListener?.onClick(DrawablePosition.LEFT)
            event.action = MotionEvent.ACTION_CANCEL

        }
    }

    private fun setupDrawableRightClick(bounds: Rect?, event: MotionEvent) {
        var xClickPosition: Int
        var yClickPosition: Int
        val extraClickingArea = 13

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
            onDrawableClickListener?.onClick(DrawablePosition.RIGHT)
            defaultClickListener?.onClick(DrawablePosition.RIGHT)
            event.action = MotionEvent.ACTION_CANCEL
        }
    }

    fun setDrawableClickListener(OnDrawableClickListener: OnDrawableClickListener) {
        this.onDrawableClickListener = OnDrawableClickListener
    }

    interface OnDrawableClickListener {
        fun onClick(target: DrawablePosition)
    }

    @Keep
    enum class DrawablePosition {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    private class EmojiExcludeFilter : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    return ""
                }
            }
            return null
        }
    }
}
