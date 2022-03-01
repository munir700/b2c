package com.yap.uikit.widgets.dialerpad

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.yap.uikit.R
import com.yap.uikit.databinding.LayoutKeyIconBinding
import com.yap.uikit.databinding.LayoutKeyTextBinding
import com.yap.uikit.widget.dialerpad.KeyClickListener

/**
 *
 * This custom view will shape Keys automatically
 * based on available size (width/height).
 */
class AdjustableDialerPad : LinearLayout, View.OnClickListener {
    private val DEFAULT_CODE_MIN_LENGTH = 4
    private val DEFAULT_CODE_MAX_LENGTH = 6
    private lateinit var mInflater: LayoutInflater
    private var mCellSize: Int = 0
    private var mKeyClickListener: KeyClickListener? = null
    private var mListener: OnSecureCodeListener? = null
    private var leftDrawableResId: Int? = null
    private var rightDrawableResId: Int? = null
    private var rightKey: View? = null
    private var leftKey: View? = null
    private var mCodeMinLength: Int = DEFAULT_CODE_MIN_LENGTH
    private var mCodeMaxLength: Int = DEFAULT_CODE_MAX_LENGTH
    private var mCode: String = ""
    private var rightKeyVisibility: Boolean? = false
    private var maxWidth: Int = 0
    private var margin: Int = 0
    private var btnMarginTop: Int = 0
    private var btnMarginStart: Int = 0
    private var btnMarginBottom: Int = 0
    private var btnMarginEnd: Int = 0
    private var isPassCodeCreated = false
    private var rightIconPadding: Int = 0
    private var leftIconPadding: Int = 0
    private var rightIconScaleType: ScaleType? = null
    private var leftIconScaleType: ScaleType? = null
    private var leftIconScaleTypeIndex: Int = 0
    private var rightIconScaleTypeIndex: Int = 0
    private var isEnabledDialerPad: Boolean? = null
    private val keypads: ArrayList<View> = ArrayList()

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mInflater = LayoutInflater.from(context)
        attrs?.let {
//        val maxWidth = resources.getDimensionPixelSize(R.dimen._100sdp)
            val typedArray =
                getContext().obtainStyledAttributes(attrs, R.styleable.AdjustableDialerPad)

            rightDrawableResId = typedArray.getResourceId(
                R.styleable.AdjustableDialerPad_adpRightIcon,
                R.drawable.ic_touch_id
            )
            leftDrawableResId = typedArray.getResourceId(
                R.styleable.AdjustableDialerPad_adpLeftIcon,
                R.drawable.ic_delete
            )
            maxWidth = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpBtnMaxWidth,
                resources.getDimensionPixelSize(R.dimen._100sdp)
            )
            margin = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpBtnMargin,
                resources.getDimensionPixelSize(R.dimen.ui_kit_small_margin)
            )
            btnMarginTop = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpBtn_MarginTop,
                resources.getDimensionPixelSize(R.dimen.ui_kit_small_margin)
            )
            btnMarginStart = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpBtn_MarginStart,
                resources.getDimensionPixelSize(R.dimen.ui_kit_small_margin)
            )
            btnMarginEnd = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpBtn_MarginEnd,
                resources.getDimensionPixelSize(R.dimen.ui_kit_small_margin)
            )
            btnMarginBottom = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpBtn_MarginBottom,
                resources.getDimensionPixelSize(R.dimen.ui_kit_small_margin)
            )
            mCodeMaxLength = typedArray.getInt(
                R.styleable.AdjustableDialerPad_adpCodeMaxLength,
                DEFAULT_CODE_MAX_LENGTH
            )
            mCodeMinLength = typedArray.getInt(
                R.styleable.AdjustableDialerPad_adpCodeMinLength,
                DEFAULT_CODE_MIN_LENGTH
            )

            leftIconPadding = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpLeftIconPadding,
                resources.getDimensionPixelSize(R.dimen.ui_kit_margin_zero_dp)
            )
            rightIconPadding = typedArray.getDimensionPixelSize(
                R.styleable.AdjustableDialerPad_adpRightIconPadding,
                resources.getDimensionPixelSize(R.dimen.ui_kit_margin_zero_dp)
            )

            leftIconScaleTypeIndex = typedArray.getInt(
                R.styleable.AdjustableDialerPad_adpLeftIconScaleType,
                -1
            )
            rightIconScaleTypeIndex = typedArray.getInt(
                R.styleable.AdjustableDialerPad_adpRightIconScaleType,
                -1
            )
            if (leftIconScaleTypeIndex > -1) {
                val types = ScaleType.values()
                leftIconScaleType = types[leftIconScaleTypeIndex]
            }
            if (rightIconScaleTypeIndex > -1) {
                val types = ScaleType.values()
                rightIconScaleType = types[rightIconScaleTypeIndex]
            }
            rightKeyVisibility =
                typedArray.getBoolean(R.styleable.AdjustableDialerPad_rightKeyVisibility, false)

            typedArray.recycle()
        }
        gravity = Gravity.CENTER_HORIZONTAL
        orientation = VERTICAL
//        setPadding(0, resources.getDimensionPixelSize(R.dimen.margin_medium_large), 0, 0)
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                if (childCount <= 0)
                    measureSizeAndAdd()
            }

        })
    }

    private fun measureSizeAndAdd() {
        val cellWidth = measuredWidth / 3
        val cellHeight = measuredHeight / 4
        mCellSize = maxWidth.coerceAtMost(cellWidth.coerceAtMost(cellHeight)) - margin * 2
        addItems()
    }

    private fun getLayoutParam(topMargin: Int = 0, bottomMargin: Int = 0): LayoutParams {
        val param = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        param.topMargin = topMargin
        param.bottomMargin = bottomMargin
        return param
    }

    private fun addItems() {
        addView(getDigitsRow(1, 3), getLayoutParam(bottomMargin = margin))
        addView(getDigitsRow(4, 6), getLayoutParam(margin + margin / 2, margin))
        addView(getDigitsRow(7, 9), getLayoutParam(margin + margin / 2, margin))
        val lastRow = LinearLayout(context)
        lastRow.orientation = HORIZONTAL
        lastRow.gravity = Gravity.CENTER_HORIZONTAL
        //leftIconScaleType,
        //            leftIconPadding
        val left = getIconKey(
            lastRow,
            leftDrawableResId,
            KeyEvent.LEFT,
            leftIconScaleType,
            leftIconPadding
        )
        (left.root.layoutParams as MarginLayoutParams).marginEnd = margin * 2
        (left.root.layoutParams as MarginLayoutParams).marginStart = margin
        val right = getIconKey(
            lastRow,
            rightDrawableResId,
            KeyEvent.RIGHT,
            rightIconScaleType,
            rightIconPadding
        )
        (right.root.layoutParams as MarginLayoutParams).marginStart = margin * 2
        (right.root.layoutParams as MarginLayoutParams).marginEnd = margin
        leftKey = left.root
        rightKey = right.root
        lastRow.addView(rightKey)
        val zero = getTextualKey(lastRow, "0")
        (zero.root.layoutParams as MarginLayoutParams).marginStart =
            margin + margin / 2
        (zero.root.layoutParams as MarginLayoutParams).marginEnd =
            margin + margin / 2
        lastRow.addView(zero.root)
        lastRow.addView(leftKey)
        addView(lastRow, getLayoutParam(topMargin = margin + margin / 2))
        setRightKeyVisibility(rightKeyVisibility)
        isEnabledDialerPad(isEnabledDialerPad)
    }

    private fun getDigitsRow(start: Int, end: Int): LinearLayout {
        val rowContainer = LinearLayout(context)
        rowContainer.orientation = HORIZONTAL
        rowContainer.gravity = Gravity.CENTER_HORIZONTAL
        var i: Int = 1
        for (value in start.rangeTo(end)) {
            val textBinding = getTextualKey(rowContainer, "$value")
            when {
                value == start -> {
                    (textBinding.root.layoutParams as MarginLayoutParams).marginEnd = margin
                    (textBinding.root.layoutParams as MarginLayoutParams).marginStart = margin * 2
                }
                value == end -> {
                    (textBinding.root.layoutParams as MarginLayoutParams).marginStart = margin
                    (textBinding.root.layoutParams as MarginLayoutParams).marginEnd = margin * 2
                }
                i == (end - start) -> {
                    (textBinding.root.layoutParams as MarginLayoutParams).marginStart =
                        margin + margin / 2
                    (textBinding.root.layoutParams as MarginLayoutParams).marginEnd =
                        margin + margin / 2
                }
            }
            rowContainer.addView(textBinding.root)
            i++
        }
        return rowContainer
    }

    private fun getTextualKey(
        rowContainer: LinearLayout,
        value: String
    ): LayoutKeyTextBinding {
        val textBinding = LayoutKeyTextBinding.inflate(mInflater, rowContainer, false)
        textBinding.text.text = value
        textBinding.root.layoutParams.height = mCellSize
        textBinding.root.layoutParams.width = mCellSize
        textBinding.root.tag = value
        textBinding.root.setOnClickListener(this)
        keypads.add(textBinding.text)
        return textBinding
    }

    private fun getIconKey(
        rowContainer: LinearLayout,
        drawableResId: Int?, keyEvent: KeyEvent,
        scaleType: ScaleType? = null,
        padding: Int
    ): LayoutKeyIconBinding {
        val iconBinding = LayoutKeyIconBinding.inflate(mInflater, rowContainer, false)
        drawableResId?.let { iconBinding.icon.setImageResource(it) }
        iconBinding.root.layoutParams.height = mCellSize
        iconBinding.root.layoutParams.width = mCellSize
        iconBinding.icon.setPadding(padding, padding, padding, padding)
        scaleType?.let {
            iconBinding.icon.scaleType = it
        }
        iconBinding.root.tag = keyEvent
        iconBinding.root.setOnClickListener(this)
        keypads.add(iconBinding.root)
        return iconBinding
    }

    fun setKeyClickListener(listener: KeyClickListener?) {
        mKeyClickListener = listener
    }

    override fun onClick(v: View?) {
        val keyEvent: KeyEvent = when (val value = v?.tag) {
            "0" -> KeyEvent.ZERO
            "1" -> KeyEvent.ONE
            "2" -> KeyEvent.TWO
            "3" -> KeyEvent.THREE
            "4" -> KeyEvent.FOUR
            "5" -> KeyEvent.FIVE
            "6" -> KeyEvent.SIX
            "7" -> KeyEvent.SEVEN
            "8" -> KeyEvent.EIGHT
            "9" -> KeyEvent.NINE
            else -> value as KeyEvent
        }
        mKeyClickListener?.onKeyClicked(v, keyEvent)
    }

    fun input(number: String): Int {
        if (number.toInt() < 0)
            return mCode.length
        if (mCode.length == mCodeMaxLength) {
            return mCode.length
        }
        mCode += number
        mListener?.onCodeChange(mCode)
        if (mCode.length >= mCodeMinLength) {
            mListener?.onCodeCompleted(mCode, true)
        }
        return mCode.length
    }

    fun delete(): Int {
        if (mCode.isEmpty()) {
            mListener?.onCodeCompleted("", false)
            return mCode.length
        }
        mCode = mCode.substring(0, mCode.length - 1)
        mListener?.onCodeChange(mCode)
        if (mCode.length < mCodeMinLength)
            mListener?.onCodeCompleted(mCode, false)
        return mCode.length
    }

    fun clearCode() {
        mListener?.onCodeCompleted(mCode, false)
        mCode = ""
    }

    fun getInputCodeLength(): Int {
        return mCode.length
    }

    fun setOnSecureCodeListener(listener: OnSecureCodeListener) {
        mListener = listener
    }

    fun setRightKeyVisibility(rightKeyVisibility: Boolean?) {

        rightKey?.let {
            it.visibility = if (rightKeyVisibility == true) View.VISIBLE else View.INVISIBLE
        }
    }

    fun isEnabledDialerPad(enabled: Boolean?) {
        enabled?.let {
            if (!it)
                for (view in keypads) {
                    view.isEnabled = false
                    view.alpha = 0.5f
                }
            else
                for (view in keypads) {
                    view.isEnabled = true
                    view.alpha = 1f
                }
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter(value = ["rightKeyVisibility"])
        fun setRightKeyVisibility(view: AdjustableDialerPad, rightKeyVisibility: Boolean?) {
            view.rightKeyVisibility = rightKeyVisibility
            view.setRightKeyVisibility(rightKeyVisibility)
        }

        @JvmStatic
        @BindingAdapter(value = ["dialerPadEnabled"])
        fun setIsEnabledDialerPad(view: AdjustableDialerPad, isEnabled: Boolean?) {
            view.isEnabledDialerPad = isEnabled
            view.isEnabledDialerPad(isEnabled)
        }
    }
}