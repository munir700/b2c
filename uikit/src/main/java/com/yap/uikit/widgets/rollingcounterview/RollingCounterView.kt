package com.yap.uikit.widgets.rollingcounterview

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.google.android.material.textview.MaterialTextView
import com.yap.uikit.R
import java.util.*

@SuppressLint("ResourceType")
class RollingCounterView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private val ANIMATION_DURATION = 100
    var currentTextView: MaterialTextView? = null
    var nextTextView: MaterialTextView? = null
    var layout: FrameLayout? = null
    private var mBackground: Drawable?
    private var mTextColor: Int?
    private var mTextStyle: Int? = null

//    constructor(context: Context) : super(context) {
//        init(context = context)
//    }

    init {
        val attributes: TypedArray =
            context.obtainStyledAttributes(attrs, R.styleable.RollingCounterView)
        mBackground = attributes.getDrawable(
            R.styleable.RollingCounterView_rcvBackground
        )
        mTextColor = attributes.getColor(
            R.styleable.RollingCounterView_rcvTextColor,
            resources.getColor(R.color.rolling_counter_view_text_color_default, context.theme)
        )
        init(context = context)
    }


    private fun init(context: Context?) {
        LayoutInflater.from(context).inflate(R.layout.layout_rolling_counter, this)
        currentTextView = rootView.findViewById(R.id.currentTextView)
        nextTextView = rootView.findViewById(R.id.nextTextView)
        layout = rootView.findViewById(R.id.flayout)
        nextTextView?.translationY = height.toFloat()
        setViews()
        setValue(9)
    }

    private fun setViews() {
        mBackground?.let {
            layout?.background = mBackground
        }
        mTextStyle?.let {
            nextTextView?.setTextAppearance(it)
            currentTextView?.setTextAppearance(it)
        }

        mTextColor?.let {
            nextTextView?.setTextColor(it)
            currentTextView?.setTextColor(it)
        }

    }

    fun setValue(realValue: Int) {
        if (currentTextView?.text == null || currentTextView?.text?.isEmpty() == true) {
            currentTextView?.text = String.format(Locale.getDefault(), "%d", realValue)
        }
        val oldValue = currentTextView?.text.toString().toInt()

        if (oldValue > realValue) {
            nextTextView?.text = String.format(Locale.getDefault(), "%d", oldValue - 1)
            currentTextView?.animate()?.translationY(-height.toFloat())
                ?.setDuration(ANIMATION_DURATION.toLong())?.start()
            nextTextView?.translationY = (nextTextView?.height?.toFloat() ?: 0f)
            nextTextView?.animate()?.translationY(0f)
                ?.setDuration(ANIMATION_DURATION.toLong())
                ?.setListener(getAnimator(oldValue - 1, realValue))?.start()
        } else if (oldValue < realValue) {
            nextTextView?.text = String.format(Locale.getDefault(), "%d", oldValue + 1)
            currentTextView?.animate()?.translationY(height.toFloat())
                ?.setDuration(ANIMATION_DURATION.toLong())?.start()
            nextTextView?.translationY = -(nextTextView?.height?.toFloat() ?: 0f)
            nextTextView?.animate()?.translationY(0f)
                ?.setDuration(ANIMATION_DURATION.toLong())
                ?.setListener(getAnimator(oldValue + 1, realValue))?.start()
        }
    }

    private fun getAnimator(oldValue: Int, realValue: Int): Animator.AnimatorListener {
        return object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                currentTextView?.text =
                    String.format(Locale.getDefault(), "%d", oldValue)
                currentTextView?.translationY = 1f
                if (oldValue != realValue) {
                    setValue(realValue)
                }
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }
    }

    var rcvBackground: Drawable? = null
        set(value) {
            field = value
            rcvBackground?.let {
                mBackground = it
                setViews()
                invalidate()
            }
        }

    var rcvTextStyle: Int? = null
        set(value) {
            field = value
            rcvTextStyle?.let {
                mTextStyle = it
                setViews()
                invalidate()
            }
        }

    var rcvTextColor: Int? = null
        set(value) {
            field = value
            rcvTextColor?.let {
                mTextColor = it
                setViews()
                invalidate()
            }
        }
}