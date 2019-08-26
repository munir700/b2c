package co.yap.modules.dashboard

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import co.yap.R

//class ChartView


class ChartView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    internal var paint: Paint

    private var btnWeight: Int = 50
    private var btnHeight: Int = 400
    private var roundRadius: Int = 20
    private var btnRadius: Int = 0

    init {
        paint = Paint()
    }


    //    @SuppressLint("ResourceAsColor")
//    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceType")
    protected override fun onDraw(canvas: Canvas) {
        paint.setStyle(Paint.Style.FILL) // set style to paint
//        paint.setColor(-0x17f6da) // set color
        paint.setStrokeWidth(10F) // set stroke width
        var rectF: RectF = RectF()
        paint.setColor(context.resources.getColor(R.color.greyDark))


//        paint.shader = LinearGradient(0f, 0f, width.toFloat(), 0f, Color.WHITE, Color.BLUE, Shader.TileMode.MIRROR)


//        paint.shader = LinearGradient(
//            10f,
//            10f,
//            width.toFloat(),
//            10f,
//            Color.WHITE,
//            Color.BLUE,
//            Shader.TileMode.MIRROR
//        )
//        GradientDrawable
//        val rGradient: RadialGradient = RadialGradient(
//            centerX.toFloat(),
//            centerY.toFloat(),
//            width.toFloat() / 2,
//            intArrayOf(Color.BLACK, Color.GREEN),
//            null,
//            Shader.TileMode.MIRROR
//        )


//        val rGradient = GradientDrawable(
//            GradientDrawable.Orientation.TOP_BOTTOM,
//            intArrayOf(
//                ContextCompat.getColor(context, Color.GREEN),
//                ContextCompat.getColor(context, Color.BLACK)
//
//            )
//        )
        val shader: LinearGradient = LinearGradient(
            0f,
            100f,
            width.toFloat(),
            0f,
            Color.DKGRAY,
            Color.BLUE,
            Shader.TileMode.CLAMP
        )
//            LinearGradient(0f, h - 100f, 0f, h, -0x1f,  Color.WHITE, Color.BLUE, Shader.TileMode.CLAMP)
//            LinearGradient(0, h - GRADIENT_HEIGHT, 0, h, -0x1, 0x00FFFFFF, Shader.TileMode.CLAMP)


//        val rGradient:RadialGradient = RadialGradient(
//            centerX,
//            centerY,
//            240,
//            Color.WHITE,
//            Color.TRANSPARENT,
//            Shader.TileMode.CLAMP
//        )
        paint.shader = shader

//        paint.shader = RadialGradient(0f, 0f, width.toFloat(), 0f, Color.WHITE, Color.BLUE, Shader.TileMode.MIRROR)

//        canvas.drawRect(50F, 50F, 50F, 50F, paint) // draw rectangle
//        canvas.drawRect(100F, 100F, 250F, 250F, paint) // draw other rectangle
//        rectF.set(0f, 0f, 100F, 100.toFloat())
//        canvas.drawRoundRect(rectF, 2.toFloat(), 2.toFloat(), paint)
        rectF.set(0f, 0f, btnWeight.toFloat(), btnHeight.toFloat())
//        paint.shader = LinearGradient(0f, 0f, width.toFloat(), 0f, Color.WHITE, Color.BLUE, Shader.TileMode.MIRROR)

        canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)
//        paint.shader
//        canvas.drawText(text.toString(), (btnWeight / alignmentDistnce).toFloat(), (btnHeight / 1.6).toFloat(), paintText)

//        val bg = getBackground()
//        if (background is ShapeDrawable) {
//            (background as ShapeDrawable).getPaint()
//                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
//        } else if (background is GradientDrawable) {
//            (background as GradientDrawable).setColor(
//                ContextCompat.getColor(
//                    context,
//                    R.color.colorSecondaryMagenta
//                )
//            )
//        } else if (background is ColorDrawable) {
//            (background as ColorDrawable).setColor(
//                ContextCompat.getColor(
//                    context,
//                    R.color.greyDark
//                )
//            )
//        }
//        var clr:Int=  ContextCompat.getColor(
//            context,
//            R.color.greyDark
//        )


//        setGradientColors(R.color.greyDark,R.color.colorSecondaryGreen)
//        this.background=bg
//        paint.setColor(R.color.greyDark) // set color

    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun setGradientColors(bottomColor: Int, topColor: Int) {
//        val gradient = GradientDrawable(
//            GradientDrawable.Orientation.BOTTOM_TOP,
//            intArrayOf(bottomColor, topColor)
//        )
//        gradient.shape = GradientDrawable.RECTANGLE
//        gradient.cornerRadius = 10f
//        this.setBackgroundDrawable(gradient)
//        val magnitudeCircle = getBackground() as GradientDrawable
//
//
////        val drawableId : Int = Color.parseColor((magnitudeCircle as Drawable).toString())
//////
//////
//////        paint.setColor()
////        paint.setColor(drawableId)
//    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(btnWeight, btnHeight) // set desired width and height of your
//        setMeasuredDimension(100, 400) // set desired width and height of your
        // layout
    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        btnWeight = w
        btnHeight = h
        btnRadius = btnWeight / 2
    }

}