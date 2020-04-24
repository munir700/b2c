package co.yap.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.LinearLayout
import co.yap.yapcore.R
import java.util.*

private const val DEFAULT_CODE_LENGTH = 6

class YapPassCodeView : LinearLayout {
    var mCodeViews: MutableList<CheckBox> =
        ArrayList()
    var code = ""
        private set
    private var mCodeLength = DEFAULT_CODE_LENGTH
        set(value) {
            field = value
            setUpCodeViews()
        }


    val inputCodeLength: Int
        get() = code.length
    private var mListener: OnYapPassCodeViewListener? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        View.inflate(
            context,
            R.layout.view_code_pf_lockscreen,
            this
        )
        setUpCodeViews()
    }

//    fun setCodeLength(codeLength: Int) {
//        mCodeLength = codeLength
//        setUpCodeViews()
//    }

    private fun setUpCodeViews() {
        removeAllViews()
        mCodeViews.clear()
        code = ""
        for (i in 0 until mCodeLength) {
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(
                R.layout.layout_yap_pass_code_view,
                null
            ) as CheckBox
            val layoutParams =
                LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            val margin =
                resources.getDimensionPixelSize(R.dimen.margin_extra_small)
            layoutParams.setMargins(margin, margin, margin, margin)
            view.layoutParams = layoutParams
            view.isChecked = false
            addView(view)
            mCodeViews.add(view)
        }
        mListener?.onCodeNotCompleted("")
    }

    fun input(number: String): Int {
        if (code.length == mCodeLength) {
            return code.length
        }
        mCodeViews[code.length].toggle() //.setChecked(true);
        code += number
        if (code.length == mCodeLength && mListener != null) {
            mListener!!.onCodeCompleted(code)
        }
        return code.length
    }

    fun delete(): Int {

        mListener?.onCodeNotCompleted(code)

        if (code.length == 0) {
            return code.length
        }
        code = code.substring(0, code.length - 1)
        mCodeViews[code.length].toggle() //.setChecked(false);
        return code.length
    }

    fun clearCode() {
        mListener?.onCodeNotCompleted(code)
        code = ""
        for (codeView in mCodeViews) {
            codeView.isChecked = false
        }
    }


    fun seOnYapPassCodeViewListener(listener: OnYapPassCodeViewListener?) {
        mListener = listener
    }

    interface OnYapPassCodeViewListener {
        fun onCodeCompleted(code: String?)
        fun onCodeNotCompleted(code: String?)
    }

    fun animateOnError(vibrator: Boolean = false) {
//            final Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
//            if (v != null) {
//                v.vibrate(400);
//            }
//
//
        val animShake =
            AnimationUtils.loadAnimation(
                context,
                R.anim.shake
            )
        startAnimation(animShake)
    }

}