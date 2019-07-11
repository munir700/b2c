package co.yap.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button
import co.yap.yapcore.R


class CoreButton : Button {

    private var btnWeight: Int = 0
    private var btnHeight: Int = 0
    private var labelTextColor: Int = 0
    private var pressedColor: Int = 0
    private var defaultStateColor: Int = 0
    private var roundRadius: Int = 0
    private var btnRadius: Int = 0
    private var shapeType: Int = 0
    private var DRAWABLE_RIGHT: Int = 1
    private var DRAWABLE_LEFT: Int = 0
    private var enableButton: Boolean = true

    private var defaultDrawablePaddingLeft: Float = 9.5f
    private var defaultDrawablePaddingRight: Float = 1.2f
    private var defaultDrawablePaddingTop: Float = 2.5f

    private var drawablePaddingLeft: Float = 9.5f
    private var drawablePaddingRight: Float = 1.2f
    private var drawablePaddingTop: Float = 2.5f

    private var drawablePositionType: Int = 0
    private var labelTextSize: Float = 0f
    var drawable: Drawable? = null
    private var paintText: Paint = Paint()
    private var paint: Paint = Paint()
    private var rectF: RectF = RectF()
    lateinit var bitmapIcon: Bitmap


    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs,
        defStyleAttr
    ) {
        init(context, attrs)

    }

    @Suppress("DEPRECATION")
    private fun init(context: Context, attrs: AttributeSet) {
        if (isInEditMode) {
            return
        }

        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.CoreButton)

        paint.style = Paint.Style.STROKE

        drawable = typedArray.getDrawable(
            R.styleable.CoreButton_btn_drawable
        )
        drawablePositionType = typedArray.getInt(R.styleable.CoreButton_btn_drawable_position, 2)
        enableButton = typedArray.getBoolean(R.styleable.CoreButton_btn_enable, enableButton)


        labelTextColor = typedArray.getColor(
            R.styleable.CoreButton_btn_text_color,
            resources.getColor(R.color.white)
        )

        if (enableButton) {
            defaultStateColor = typedArray.getColor(
                R.styleable.CoreButton_btn_unpressed_color,
                resources.getColor(R.color.colorPrimary)
            )
            pressedColor = typedArray.getColor(
                R.styleable.CoreButton_btn_pressed_color,
                resources.getColor(R.color.colorPrimaryDark)
            )

        } else {
            defaultStateColor = resources.getColor(R.color.greyLight)
            pressedColor = resources.getColor(R.color.greyLight)
        }

        shapeType = typedArray.getInt(R.styleable.CoreButton_btn_shape_type, 1)
        drawablePaddingLeft =
            typedArray.getFloat(R.styleable.CoreButton_btn_drawable_padding_left, defaultDrawablePaddingLeft)
        drawablePaddingRight =
            typedArray.getFloat(R.styleable.CoreButton_btn_drawable_padding_right, defaultDrawablePaddingRight)
        drawablePaddingTop = typedArray.getFloat(R.styleable.CoreButton_btn_drawable_padding_top, drawablePaddingTop)

        roundRadius = typedArray.getDimensionPixelSize(
            R.styleable.CoreButton_btn_round_radius,
            resources.getDimensionPixelSize(R.dimen.round_radius)
        )

        labelTextSize = typedArray.getDimensionPixelSize(
            R.styleable.CoreButton_btn_text_size,
            resources.getDimensionPixelSize(R.dimen.label_text_size)
        ).toFloat()

        typedArray.recycle()
        paint.color = defaultStateColor
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        this.setWillNotDraw(false)
        this.isDrawingCacheEnabled = true
        this.isClickable = true
        this.setBackgroundColor(resources.getColor(R.color.transparent))

        /* text paint styling */

        paintText.setColor(labelTextColor)
        paintText.setTextSize(labelTextSize)
        paintText.textAlign = Paint.Align.CENTER
        paintText.style = Paint.Style.FILL

    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap?

        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }

        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap =
                Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }

        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        btnWeight = w
        btnHeight = h
        btnRadius = btnWeight / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (shapeType == 0) {
            canvas.drawCircle(
                (btnWeight / 2).toFloat(), (btnHeight / 2).toFloat(), btnRadius.toFloat(),
                paint
            )
        } else {
            rectF.set(0f, 0f, btnWeight.toFloat(), btnHeight.toFloat())
            canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)
            canvas.drawText(text.toString(), (btnWeight / 2).toFloat(), (btnHeight / 1.6).toFloat(), paintText)

        }

        if (null != drawable) {
            bitmapIcon = drawable?.let { this!!.drawableToBitmap(it) }!!


            when (drawablePositionType) {
                DRAWABLE_LEFT -> canvas.drawBitmap(
                    bitmapIcon,
                    (btnWeight / drawablePaddingLeft).toFloat(),    //position from left
                    (btnHeight / drawablePaddingTop).toFloat(),     // set y-position of drawableRight left from top
                    paintText
                )

                DRAWABLE_RIGHT -> canvas.drawBitmap(
                    bitmapIcon,
                    (btnWeight / drawablePaddingRight).toFloat(),       //position from left
                    (btnHeight / drawablePaddingTop).toFloat(),         // set y-position of drawableRight right
                    paintText
                )
                else ->
                    canvas.drawBitmap(
                        bitmapIcon,
                        (btnWeight / drawablePaddingTop).toFloat(),     //position from left
                        (btnHeight / drawablePaddingTop).toFloat(),     // set y-position of drawableRight right
                        paintText
                    )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if (this.isEnabled) {
                    paint.color = pressedColor
                    invalidate()
                }
            }

            MotionEvent.ACTION_UP -> {
                if (this.isEnabled) {
                    paint.color = defaultStateColor
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun enableButton(enable: Boolean) {

        if (!enable) {
            defaultStateColor = resources.getColor(R.color.greyLight)
            pressedColor = resources.getColor(R.color.colorPrimary)
            paint.color = defaultStateColor
            invalidate()
        } else {
            defaultStateColor = resources.getColor(R.color.colorPrimary)
            pressedColor = resources.getColor(R.color.colorPrimaryDark)
            paint.color = defaultStateColor
            invalidate()
        }
        this.setEnabled(enable)

    }
}