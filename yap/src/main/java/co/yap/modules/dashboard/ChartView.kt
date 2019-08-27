package co.yap.modules.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import co.yap.R
import java.time.Duration
import kotlin.math.roundToInt


//class ChartView
class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs),
    View.OnTouchListener {


    internal var paint: Paint

    private var btnWeight: Int = 50
    private var btnHeight: Int = 400
    private var roundRadius: Int = 20
    private var btnRadius: Int = 0

    init {
        paint = Paint()
//        this.setOnFocusChangeListener(object : View.OnFocusChangeListener {
//            override fun onFocusChange(v: View?, hasFocus: Boolean) {
//
////                paint.setColor(context.resources.getColor(R.color.colorPrimary))
//
//            }
//        })


        this.performClick()

//        this.onTouch(this)

        this.setOnTouchListener(this)
//        this.setOnTouchListener(this)


    }
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action){
            MotionEvent.ACTION_DOWN -> {

                    //                if (this.isEnabled) {
//                    paint.shader = null
//                    paint.setColor(context.resources.getColor(R.color.colorPrimary))
                paint.setColor(context.resources.getColor(R.color.colorPrimary))
                Toast.makeText(context, "ACTION_DOWN",Toast.LENGTH_SHORT).show()

//                    invalidate()
 //                }

            }
            MotionEvent.ACTION_UP -> {
                //                if (this.isEnabled) {
                Toast.makeText(context, "ACTION_UP",Toast.LENGTH_SHORT).show()

                paint.setColor(context.resources.getColor(R.color.error))

//                val shader: LinearGradient = LinearGradient(
//                    0f,
//                    100f,
//                    width.toFloat(),
//                    0f,
//                    context.resources.getColor(R.color.colorDarkGreyGradient),
//                    context.resources.getColor(R.color.colorLightGreyGradient),
//                    Shader.TileMode.CLAMP
//                )
//
//                paint.shader = shader
//                invalidate()

            }
        }
    return false
    }

    @SuppressLint("ResourceType")
    protected override fun onDraw(canvas: Canvas) {
        paint.setStyle(Paint.Style.FILL)
        paint.setStrokeWidth(10F)

        var rectF: RectF = RectF()

        //

        val shader: LinearGradient = LinearGradient(
            0f,
            100f,
            width.toFloat(),
            0f,
            context.resources.getColor(R.color.colorDarkGreyGradient),
            context.resources.getColor(R.color.colorLightGreyGradient),
            Shader.TileMode.CLAMP
        )

        paint.shader = shader
        rectF.set(0f, 0f, btnWeight.toFloat(), btnHeight.toFloat())
        canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)
//        this.setOnFocusChangeListener()

//        this.setOnFocusChangeListener(object : View.OnFocusChangeListener {
//            override fun onFocusChange(v: View?, hasFocus: Boolean) {
//
////                paint.setColor(context.resources.getColor(R.color.colorPrimary))
//               if (hasFocus)  {
//                    //                if (this.isEnabled) {
//                    paint.shader = null
//                    paint.setColor(context.resources.getColor(R.color.colorPrimary))
//
//                    invalidate()
//                    MotionEvent.ACTION_BUTTON_RELEASE
////                }
//                }else{
//                    //                if (this.isEnabled) {
//
//                    val shader: LinearGradient = LinearGradient(
//                        0f,
//                        100f,
//                        width.toFloat(),
//                        0f,
//                        context.resources.getColor(R.color.colorDarkGreyGradient),
//                        context.resources.getColor(R.color.colorLightGreyGradient),
//                        Shader.TileMode.CLAMP
//                    )
//
//                    paint.shader = shader
//                    invalidate()
//                }
//            }
//        })
//
//        this.setOnClickListener()

//        this.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                paint.setColor(context.resources.getColor(R.color.colorPrimary))
//            }
//        })

    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(btnWeight, btnHeight) // set desired width and height of your
    }

    fun setBarHeight(barHeight: Double) {
        btnHeight = barHeight.roundToInt() * 10

    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        btnWeight = w
        btnHeight = h
        btnRadius = btnWeight / 2
    }
//
//    override fun setOnTouchListener(l: OnTouchListener?) {
//        super.setOnTouchListener(l)
//        if (l.)
//    }
//            override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return super.onKeyDown(keyCode, event)
//        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            paint.shader = null
//            paint.setColor(context.resources.getColor(R.color.colorPrimary))
//            invalidate()
//            MotionEvent.ACTION_BUTTON_RELEASE
////                }
//        }else{
//            val shader: LinearGradient = LinearGradient(
//                0f,
//                100f,
//                width.toFloat(),
//                0f,
//                context.resources.getColor(R.color.colorDarkGreyGradient),
//                context.resources.getColor(R.color.colorLightGreyGradient),
//                Shader.TileMode.CLAMP
//            )
//
//            paint.shader = shader
//            invalidate()
//        }
//    }

//    override fun onTouchEvent(event: MotionEvent): Boolean {
//        when (event.action) {
//
//            MotionEvent.ACTION_DOWN -> {
////                if (this.isEnabled) {
//                    paint.shader = null
//                    paint.setColor(context.resources.getColor(R.color.colorPrimary))
//
//                    invalidate()
//                MotionEvent.ACTION_BUTTON_RELEASE
////                }
//            }
//
//            MotionEvent.ACTION_BUTTON_RELEASE -> {
////                if (this.isEnabled) {
//
//                    val shader: LinearGradient = LinearGradient(
//                        0f,
//                        100f,
//                        width.toFloat(),
//                        0f,
//                        context.resources.getColor(R.color.colorDarkGreyGradient),
//                        context.resources.getColor(R.color.colorLightGreyGradient),
//                        Shader.TileMode.CLAMP
//                    )
//
//                    paint.shader = shader
//                    invalidate()
//                }
////            }
//        }
//        return super.onTouchEvent(event)
//    }

    override fun onTouchEvent(motionEvent: MotionEvent?): Boolean {
//        when (view) {
//            next -> {
                 when (motionEvent!!.action){
                    MotionEvent.ACTION_DOWN -> {
                        paint.setColor(context.resources.getColor(R.color.colorPrimary))
                    }
                    MotionEvent.ACTION_UP -> {
                        paint.setColor(context.resources.getColor(R.color.error))

                    }
                }
//            }
//            previous -> {
//                //ingredients here XD
//            }
//        }
        return true
    }
}