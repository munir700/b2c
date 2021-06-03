package co.yap.widgets.radiocus

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import co.yap.yapcore.R
import java.util.*

class PresetValueButton : CardView, RadioCheckable {

    private var tvLable1: TextView? = null
    private var tvLable2: TextView? = null
    var lable1: String? = null
    var lable2: String? = null
    private var lable1Color = 0
    private var lable2Color = 0
    private var mPressedTextColor = 0
    private var layoutResId = -1
    private var mInitialBackgroundDrawable: Drawable? = null
    private var mDrawableSelected: Int = 0
    private var mDrawableNormal: Int = 0
    private var mOnClickListener: OnClickListener? = null
    var onTouchListener: OnTouchListener? = null
        private set
    private var mChecked = false
    private val mOnCheckedChangeListeners =
        ArrayList<RadioCheckable.OnCheckedChangeListener?>()

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
        parseAttributes(attrs)
        setupView()
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(
            attrs,
            R.styleable.PresetValueButton, 0, 0
        )
        val resources = context.resources
        try {
            layoutResId = a.getResourceId(R.styleable.PresetValueButton_preset_layoutResId, -1)
            lable1 = a.getString(R.styleable.PresetValueButton_lable1)
            lable2 = a.getString(R.styleable.PresetValueButton_lable2)
            lable1Color = a.getColor(
                R.styleable.PresetValueButton_lable1Color,
                resources.getColor(R.color.kyc_success_text_color_light_gray, null)
            )
            lable2Color = a.getColor(
                R.styleable.PresetValueButton_lable2Color,
                resources.getColor(R.color.colorPrimaryDark, null)
            )
            mPressedTextColor =
                a.getColor(
                    R.styleable.PresetValueButton_presetButtonPressedTextColor,
                    resources.getColor(R.color.colorAccentHouseHold, null)
                )
            mDrawableSelected =
                a.getResourceId(
                    R.styleable.PresetValueButton_preset_drawableSelected,
                    -1
                )
            mDrawableNormal =
                a.getResourceId(
                    R.styleable.PresetValueButton_preset_drawableNormal,
                    -1
                )
        } finally {
            a.recycle()
        }
    }

    private fun setupView() {
        if (layoutResId > -1) {
            inflateView()
            bindView()
        }
        if (mDrawableNormal > -1) setBackgroundResource(mDrawableNormal)
        else
            mInitialBackgroundDrawable = background
        setCustomTouchListener()
    }

    private fun inflateView() {
        val inflater =
            LayoutInflater.from(context)
        inflater.inflate(layoutResId, this, true)
        tvLable1 = findViewById(R.id.tvLable1)
        tvLable2 = findViewById(R.id.tvLable2)
    }

    private fun bindView() {
        if (lable1Color != DEFAULT_TEXT_COLOR) {
            tvLable1?.setTextColor(lable1Color)
        }
        if (lable2Color != DEFAULT_TEXT_COLOR) {
            tvLable2?.setTextColor(lable2Color)
        }
        tvLable2?.text = lable2
        tvLable1?.text = lable1
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mOnClickListener = l
    }

    private fun setCustomTouchListener() {
        super.setOnTouchListener(TouchListener())
    }

    override fun setOnTouchListener(onTouchListener: OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

    private fun onTouchDown(motionEvent: MotionEvent) {
        setChecked(true)
    }

    private fun onTouchUp(motionEvent: MotionEvent) {
        mOnClickListener?.let {
            mOnClickListener?.onClick(this)
        }
    }

    private fun setCheckedState() {
        if (mDrawableSelected > -1) setBackgroundResource(mDrawableSelected)
        else setBackgroundResource(R.drawable.background_shape_preset_button_pressed)
        tvLable1?.setTextColor(mPressedTextColor);
    }

    private fun setNormalState() {
        if (mDrawableNormal > -1) setBackgroundResource(mDrawableNormal)
        else background = mInitialBackgroundDrawable
        tvLable1?.setTextColor(lable1Color);
    }

    override fun setChecked(checked: Boolean) {
        if (mChecked != checked) {
            mChecked = checked
            if (mOnCheckedChangeListeners.isNotEmpty()) {
                for (i in mOnCheckedChangeListeners.indices) {
                    mOnCheckedChangeListeners[i]?.onCheckedChanged(this, mChecked)
                }
            }
            if (mChecked) {
                setCheckedState()
            } else {
                setNormalState()
            }
        }
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        setChecked(!mChecked)
    }

    override fun addOnCheckChangeListener(onCheckedChangeListener: RadioCheckable.OnCheckedChangeListener?) {
        mOnCheckedChangeListeners.add(onCheckedChangeListener)
    }

    override fun removeOnCheckChangeListener(onCheckedChangeListener: RadioCheckable.OnCheckedChangeListener?) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener)
    }

    private inner class TouchListener : OnTouchListener {
        override fun onTouch(
            v: View,
            event: MotionEvent
        ): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> onTouchDown(event)
                MotionEvent.ACTION_UP -> onTouchUp(event)
            }
            if (onTouchListener != null) {
                onTouchListener?.onTouch(v, event)
            }
            return true
        }
    }

    fun setLabel2(value: String) {
        tvLable2?.text = value
    }

    companion object {
        const val DEFAULT_TEXT_COLOR = Color.TRANSPARENT
    }
}
