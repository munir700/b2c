package co.yap.widgets.currency

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import co.yap.app.YAPApplication
import co.yap.yapcore.R
import co.yap.yapcore.helpers.DecimalDigitsInputFilter
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.Utils.dpToFloat
import co.yap.yapcore.helpers.extentions.dip2px
import co.yap.yapcore.helpers.extentions.getColors


class CoreEditText : AppCompatEditText {

    private val defaultPadding = 15
    private var mBackgroundColor: Int = 0
    private var cPadding: Int = 0
    private var cPaddingLeft: Int = 5
    private var cPaddingTop: Int = 0
    private var cPaddingRight: Int = 0
    private var cPaddingBottom: Int = 0

    //private var decimals: Int = YAPApplication.selectedCurrency
    private var units: Int = 10
    private var mCornerRadius: Float = dpToFloat(context, 14f)
    private var mStrokeWidth = dpToFloat(context, 0f)
    private var isPassword = false
    private var mDrawableWidth: Int = 0
    private var mDrawableHeight: Int = 0
    private var ceSpace: Int = 50
    private var customWidth: Int = 0
    private var customHeight: Int = 0


    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CoreEditText)

        cPadding = a.getDimensionPixelSize(R.styleable.CoreEditText_android_padding, -1)
        cPaddingLeft =
            a.getDimensionPixelSize(R.styleable.CoreEditText_android_paddingLeft, defaultPadding)
        cPaddingTop =
            a.getDimensionPixelSize(R.styleable.CoreEditText_android_paddingTop, defaultPadding)
        cPaddingRight =
            a.getDimensionPixelSize(R.styleable.CoreEditText_android_paddingRight, defaultPadding)
        cPaddingBottom =
            a.getDimensionPixelSize(R.styleable.CoreEditText_android_paddingBottom, defaultPadding)
        val isBorderView = a.getBoolean(R.styleable.CoreEditText_ce_borderView, true)
        mDrawableWidth = a.getDimensionPixelSize(
            R.styleable.CoreEditText_ce_compoundDrawableWidth,
            -1
        )
        mDrawableHeight = a.getDimensionPixelSize(
            R.styleable.CoreEditText_ce_compoundDrawableHeight,
            -1
        )
        ceSpace = a.getDimensionPixelSize(
            R.styleable.CoreEditText_ce_space,
            10
        )
        mBackgroundColor =
            a.getColor(
                R.styleable.CoreEditText_ce_setBackgroundColor,
                context.getColors(R.color.greySoft)
            )
        units = a.getInt(R.styleable.CoreEditText_units, 2)
        //decimals = a.getInt(R.styleable.CoreEditText_decimals, YAPApplication.selectedCurrency)
        customWidth = a.getDimension(
            R.styleable.CoreEditText_ce_custom_width, context.dip2px(
                0
            ).toFloat()
        ).toInt()
        customHeight = a.getDimension(
            R.styleable.CoreEditText_ce_custom_height, context.dip2px(
                0
            ).toFloat()
        ).toInt()

        if (isBorderView) {
            setBackGroundOfLayout(getShapeBackground())
            setCursorColor(context.getColors(R.color.colorPrimary))
        } else {
            padding(false)
        }

        gravity = Gravity.CENTER
        @RequiresApi(Build.VERSION_CODES.O)
        importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
        setSingleLine()
        maxLines = 1
        isFocusableInTouchMode = true
        filters =
            arrayOf(
                InputFilter.LengthFilter(units),
                DecimalDigitsInputFilter(YAPApplication.selectedCurrency)
            )
        a.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setEditTextDimension()
    }

    private fun setEditTextDimension() {
        val dimensions = Utils.getDimensionsByPercentage(context, 50, 8)
        val params = layoutParams
        params.width = dimensions[0]
        params.height = dimensions[1]
        layoutParams = params
    }

    private fun setCursorColor(@ColorInt color: Int) = try {
        var c = color
        if (c == 0) c = getThemeAccentColor()
        // Get the cursor resource id
        var field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        field.isAccessible = true
        val drawableResId = field.getInt(this)

        // Get the editor
        field = TextView::class.java.getDeclaredField("mEditor")
        field.isAccessible = true
        val editor = field.get(this)

        // Get the drawable and set a color filter
        val drawable = ContextCompat.getDrawable(this.context, drawableResId)
        drawable?.colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(c, BlendModeCompat.SRC_IN)
        val drawables = arrayOf(drawable!!, drawable)

        // Set the drawables
        field = editor.javaClass.getDeclaredField("mCursorDrawable")
        field.isAccessible = true
        field.set(editor, drawables)
    } catch (ignored: Exception) {
    }

    private fun setBackGroundOfLayout(shape: Drawable?) {
        background = shape
        padding(true)
    }

    private fun padding(isRound: Boolean) {
        val extraPadding: Int
        val extraPad: Int
        if (isRound) {
            extraPadding = 5
            extraPad = 0
        } else {
            extraPad = 5
            extraPadding = 0
        }
        if (cPadding != -1) {
            super.setPadding(cPadding + extraPadding, cPadding, cPadding, cPadding + extraPad)
        } else {
            super.setPadding(
                cPaddingLeft + extraPadding,
                cPaddingTop,
                cPaddingRight,
                cPaddingBottom + extraPad
            )
        }
    }

    @SuppressLint("WrongConstant")
    private fun getShapeBackground(@ColorInt color: Int = context.getColors(R.color.colorPrimary)): Drawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.cornerRadius = mCornerRadius
        shape.setColor(mBackgroundColor)
        shape.setStroke(mStrokeWidth.toInt(), color)
        return shape
    }

    private fun setBorderColor() {
//        val etBg = background as GradientDrawable
//        etBg.setStroke(mStrokeWidth.toInt(), getThemeAccentColor())
    }

    public override fun onTextChanged(s: CharSequence, i: Int, i1: Int, i2: Int) {
        try {
//            if (s ==hint) {
//
//            } else {
//
//            }
//            isCursorVisible = !s.isBlank()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getThemeAccentColor(): Int {
        val colorAttr: Int =
            android.R.attr.colorAccent
        val outValue = TypedValue()
        context.theme.resolveAttribute(colorAttr, outValue, true)
        return outValue.data
    }
}