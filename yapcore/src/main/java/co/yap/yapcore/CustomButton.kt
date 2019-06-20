package co.yap.yapcore

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.Button


class CustomButton : Button {

    private var btnWeight: Int = 0
    private var btnHeight: Int = 0
    private var label: String = ""
    private var labelTextColor: Int = 0
    private var pressedColor: Int = 0
    private var defaultStateColor: Int = 0
    private var roundRadius: Int = 0
    private var btnRadius: Int = 0
    private var shapeType: Int = 0
    private var labelTextSize: Float = 0f

    private var paintText: Paint = Paint()
    private var paint: Paint = Paint()
    private var rectF: RectF = RectF()

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
            context.obtainStyledAttributes(attrs, R.styleable.CustomButton)

        paint.style = Paint.Style.STROKE

        labelTextColor = typedArray.getColor(
            R.styleable.CustomButton_text_color,
            resources.getColor(R.color.white)
        )

        label = resources.getText(
            typedArray
                .getResourceId((R.styleable.CustomButton_text), R.string.app_name)
        ).toString()

        pressedColor = typedArray.getColor(
            R.styleable.CustomButton_pressed_color,
            resources.getColor(R.color.colorPrimary)
        )

        if (this.isEnabled) {
            defaultStateColor = typedArray.getColor(
                R.styleable.CustomButton_unpressed_color,
                resources.getColor(R.color.colorPrimaryDark)
            )
        } else {
            defaultStateColor = typedArray.getColor(
                R.styleable.CustomButton_unpressed_color,
                resources.getColor(R.color.greyLight)
            )
        }

        shapeType = typedArray.getInt(R.styleable.CustomButton_btn_shape_type, 1)
        roundRadius = typedArray.getDimensionPixelSize(
            R.styleable.CustomButton_round_radius,
            resources.getDimensionPixelSize(R.dimen.round_radius)
        )
        labelTextSize = typedArray.getDimensionPixelSize(
            R.styleable.CustomButton_text_size,
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

//        set typefacae remaining

//        val typeface = resources.getFont(R.font.roboto_bold)
//        paintText.typeface = typeface

    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        btnWeight = w
        btnHeight = h
        btnRadius = btnWeight / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (paint == null) {
            return
        }

        if (shapeType == 0) {
            canvas.drawCircle(
                (btnWeight / 2).toFloat(), (btnHeight / 2).toFloat(), btnRadius.toFloat(),
                paint
            )
            val icon: Bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_back_arrow)
            canvas.drawBitmap(icon, (btnWeight / 2.5).toFloat(), (btnHeight / 2.5).toFloat(), paintText)
        } else {
            rectF.set(0f, 0f, btnWeight.toFloat(), btnHeight.toFloat())
            canvas.drawRoundRect(rectF, roundRadius.toFloat(), roundRadius.toFloat(), paint)
            canvas.drawText(label, (btnWeight / 2).toFloat(), (btnHeight / 1.6).toFloat(), paintText)

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

}
