package co.yap.modules.dashboard.home.component

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import co.yap.R
import co.yap.yapcore.helpers.ThemeColorUtils

class ChartViewV2(context: Context, attrs: AttributeSet) : View(context, attrs),
    View.OnTouchListener, View.OnFocusChangeListener {


    private var barWeight: Int = 26
    var barHeight: Float = 99f
        set(value) {
            field = ((getParentViewHeight() / 2) * (value)) + 3
            isBarValueSet = true

            layoutParams.height = field.toInt()//context.dip2px(10f)
            invalidate()
        }
    private var roundRadius: Int = 7
    private var barRadius: Int = 0
    private var seletedColor: Int = 0
    private var isBarValueSet: Boolean = false
    var needAnimation = false

    private var paintShader: Shader? = null
    private var paint: Paint = Paint()
    var rectF: RectF = RectF()


    init {
        if (!isInEditMode) {
            paintShader = LinearGradient(
                0f,
                getParentViewHeight().toFloat(),
                width.toFloat(),
                barHeight,
                ContextCompat.getColor(context, R.color.colorDarkGreyGradient),
                ContextCompat.getColor(context, R.color.colorLightGreyGradient),
                Shader.TileMode.CLAMP
            )

            seletedColor = R.color.transparent
            customizePaint(context)
        } else {

            paintShader = LinearGradient(
                0f,
                getParentViewHeight().toFloat(),
                width.toFloat(),
                barHeight,
                ContextCompat.getColor(context, R.color.colorDarkGreyGradient),
                ContextCompat.getColor(context, R.color.colorLightGreyGradient),
                Shader.TileMode.CLAMP
            )
            seletedColor = ThemeColorUtils.colorPrimaryAttribute(context)
            customizePaint(context)
        }
    }


    private fun customizePaint(context: Context) {
        paint.color = seletedColor
        paint.shader = paintShader
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 10F

    }

    @SuppressLint("ResourceType")
    protected override fun onDraw(canvas: Canvas) {
        //height * (1 - (barHeight ?: 0f))
        try {
            rectF.set(0f, 0f, barWeight.toFloat(), getParentViewHeight())
            canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        fadeOutBarAnimation()
    }

    @SuppressLint("ResourceAsColor")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                //OnBarItemTouchEvent()
            }
        }

        return true
    }

    private fun fadeOutBarAnimation() {
        val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        fadeOutBarAnimation.duration = if (needAnimation) 300 else 0
        fadeOutBarAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                paintShader = LinearGradient(
                    0f,
                    getParentViewHeight(),
                    width.toFloat(),
                    barHeight,
                    ContextCompat.getColor(context, R.color.colorDarkGreyGradient),
                    ContextCompat.getColor(context, R.color.colorLightGreyGradient),
                    Shader.TileMode.CLAMP
                )
                seletedColor = ContextCompat.getColor(context, R.color.colorDarkGreyGradient)
                invalidate()
                customizePaint(context)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        startAnimation(fadeOutBarAnimation)
    }

    private fun customizeAnimation(context: Context) {

        val fadeInBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        fadeInBarAnimation.duration = if (needAnimation) 400 else 300

        this.startAnimation(fadeInBarAnimation)

        fadeInBarAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                paint.shader = null
                paintShader = null
                paint.color = ThemeColorUtils.colorPrimaryAttribute(context)
                seletedColor = ThemeColorUtils.colorPrimaryAttribute(context)
                invalidate()
                customizePaint(context)
            }

            override fun onAnimationEnd(animation: Animation) {
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    fun unSelectHighlightedBarOnGraphClick(highlighted: Boolean) {
        fadeOutBarAnimation()
    }


    fun unSelectHighlightedBarOnTransactionCellClick(selectBar: Boolean) {
        if (selectBar) {
            fadeOutBarAnimation()
            customizeAnimation(context)
        } else {
            fadeOutBarAnimation()

        }
    }


    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        barWeight = w
        //barHeight = h.toFloat()
        barRadius = barWeight / 2
    }
    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        unSelectHighlightedBarOnTransactionCellClick(selected)
    }

    private fun getParentView() = parent as View
    private fun getParentViewHeight() =
        resources.getDimension(R.dimen._80sdp)// getParentView().height

    private fun getParentViewWidth() = getParentView().width

}