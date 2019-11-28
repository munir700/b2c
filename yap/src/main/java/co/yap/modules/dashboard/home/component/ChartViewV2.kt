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
import co.yap.yapcore.helpers.RecyclerTouchListener
import co.yap.yapcore.helpers.dip2px

class ChartViewV2(context: Context, attrs: AttributeSet) : View(context, attrs),
    View.OnTouchListener, View.OnFocusChangeListener {


    private var barWeight: Int = 26
    var barHeight: Float = 99f
        set(value) {
            field = ((getParentViewHeight() / 2) * (value)) +3
            isBarValueSet = true

            layoutParams.height = field.toInt()//context.dip2px(10f)
            //requestLayout()
            invalidate()
        }
    private var minBarHeight: Int = 0
    private var maxBarHeight: Int = 100
    private var roundRadius: Int = 7
    private var barRadius: Int = 0
    private var seletedColor: Int = 0
    var isBarHighLighted: Boolean = false
    var isBarValueSet: Boolean = false
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
            seletedColor = ContextCompat.getColor(context, R.color.colorPrimary)
            customizePaint(context)
        }

        //this.performClick()
        //setOnTouchListener(this)

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
        rectF.set(0f, 0f, barWeight.toFloat(), getParentViewHeight())
        canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(barWeight, barHeight.toInt())
//       // setMeasuredDimension(barWeight, barHeight.toInt())
//    }


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

//    fun OnBarItemTouchEvent() {
//        if (isBarHighLighted) {
//            fadeOutBarAnimation()
//        }
//        customizeAnimation(context)
//    }

    private fun fadeOutBarAnimation() {
        val fadeOutBarAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
        fadeOutBarAnimation.duration =if(needAnimation) 300 else 0
        fadeOutBarAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                paintShader = LinearGradient(
                    0f,
                    getParentViewHeight().toFloat(),
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
        fadeInBarAnimation.duration =if(needAnimation) 400 else 300

        this.startAnimation(fadeInBarAnimation)

        fadeInBarAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                paint.shader = null
                paintShader = null
                paint.color = ContextCompat.getColor(context, R.color.colorPrimary)
                seletedColor = ContextCompat.getColor(context, R.color.colorPrimary)
                invalidate()
                customizePaint(context)
            }

            override fun onAnimationEnd(animation: Animation) {
//                fadeOutBarAnimation()

            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    fun setBarHeight(height: Double) {
        // layoutParams = LinearLayout.LayoutParams(width, 0, height.toFloat())

        barHeight = (this.height * height).toFloat()

        invalidate()
    }


    fun unSelectHighlightedBarOnGraphClick(highlighted: Boolean) {
        fadeOutBarAnimation()
    }


    fun unSelectHighlightedBarOnTransactionCellClick(selectBar: Boolean) {
        if (selectBar) {
//            fadeOutBarAnimation()
//            fadeInAnimation()

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

    fun fadeOutAnimation() {


        val pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)
        val animation = ObjectAnimator.ofPropertyValuesHolder(
            this,
            pvhA
        )


        val fadeOut = AlphaAnimation(1.0f, 0.0f)
        fadeOut.interpolator = AccelerateInterpolator() //and this
        //fadeOut.startOffset = 1000
        fadeOut.duration = 3000
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

                paintShader = LinearGradient(
                    0f,
                    100f,
                    width.toFloat(),
                    0f,
                    ContextCompat.getColor(context, R.color.colorDarkGreyGradient),
                    ContextCompat.getColor(context, R.color.colorLightGreyGradient),
                    Shader.TileMode.CLAMP
                )

                val pupleSelectedColor = context.resources.getColor(R.color.greySoft)
                seletedColor = pupleSelectedColor
                invalidate()
                customizePaint(context)
                isBarHighLighted = true
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        startAnimation(fadeOut)

    }

    fun fadeInAnimation() {
        val fadeOut = AlphaAnimation(0.0f, 1.0f)
        fadeOut.interpolator = AccelerateInterpolator() //and this
        //fadeOut.startOffset = 1000
        fadeOut.duration = 100
        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {

                paint.shader = null
                paintShader = null

                val pupleSelectedColor = ContextCompat.getColor(context, R.color.colorPrimary)
                paint.color = pupleSelectedColor
                seletedColor = pupleSelectedColor
                invalidate()
                customizePaint(context)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        startAnimation(fadeOut)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)

        unSelectHighlightedBarOnTransactionCellClick(selected)
    }

    private fun getParentView() = parent as View
    private fun getParentViewHeight() =resources.getDimension(R.dimen._80sdp)// getParentView().height
    private fun getParentViewWidth() = getParentView().width

}


//forEachValid(data, barsWidth) { index, item ->
//
//    (when (index) {
//        currentXAxis -> barDrawableFocused
//        else -> barDrawableDefault
//    })?.apply {
//        //this.alpha = alpha
//        Log.d("Idex>> ", "$index")
//        val barCenterX = horizontalMidpoint + ((index - currentXAxis - currentXAxisOffsetPercent) * (barWidth + barInterval)).toInt()
//        val rect = Rect(
//            barCenterX - barWidthHalf,
//            (barsHeight * (1 - (item.getYAxis() ?: 0f))).toInt(),
//            barCenterX + barWidthHalf,
//            barsHeight)
//        bounds = rect
//
//        draw(canvas)
//    }
//}
//}