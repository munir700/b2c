package co.yap.widgets.guidedtour.description

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.graphics.Path
import co.yap.widgets.guidedtour.shape.ShapeType
import co.yap.widgets.guidedtour.utils.Utils
import kotlin.math.roundToInt


class CoachMarkInfoToolTip  : View {
    constructor(context: Context, builder: Builder) : super(context) {

        init(builder)
    }

    constructor(context: Context, attrs: AttributeSet?, builder: Builder) : super(context, attrs) {
        init(builder)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, builder: Builder) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(builder)
    }

     constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int, builder: Builder) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(builder)
    }

    private lateinit var mBuilder: Builder
    private val mPaint = Paint()
    private val mPath = Path()

    val w = 60
    val h = 60

    override fun onDraw(canvas: Canvas?) {
                  when (mBuilder.getToolTipOrientation()) {
                    Orientation.UP -> {
                        mPath.moveTo((w / 2).toFloat(), 0f)
                        mPath.lineTo((w / 2).toFloat(), 0f)
                        mPath.lineTo(w.toFloat(), h.toFloat())
                        mPath.lineTo(0f, h.toFloat())
                        mPath.close()
                        canvas?.drawPath(mPath, mPaint)
                    }
                    Orientation.DOWN -> {
                        mPath.moveTo(w.toFloat(), 0f)
                        mPath.lineTo(w.toFloat(), 0f)
                        mPath.lineTo((w / 2).toFloat(), h.toFloat())
                        mPath.lineTo(0f, 0f)
                        mPath.close()
                        canvas?.drawPath(mPath, mPaint)
                    }
                    Orientation.LEFT -> {
                        mPath.moveTo(w.toFloat(), (h / 2).toFloat())
                        mPath.lineTo(w.toFloat(), (h / 2).toFloat())
                        mPath.lineTo(0f, h.toFloat())
                        mPath.lineTo(0f, 0f)
                        mPath.close()
                        canvas?.drawPath(mPath, mPaint)
                    }
                    Orientation.RIGHT -> {
                        mPath.moveTo(0f, (h / 2).toFloat())
                        mPath.lineTo(0f, (h / 2).toFloat())
                        mPath.lineTo(w.toFloat(), h.toFloat())
                        mPath.lineTo(w.toFloat(), 0f)
                        mPath.close()
                        canvas?.drawPath(mPath, mPaint)
                    }
        }
        super.onDraw(canvas)
    }

    private fun init(builder: Builder) {
        this.setWillNotDraw(false)
        mBuilder = builder
        mPaint.color = mBuilder.getToolTipColor()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.FILL_AND_STROKE

        mPath.fillType = Path.FillType.EVEN_ODD



    }

    class Builder(private val mContext: Context) {
        private var mToolTipShape: ShapeType = ShapeType.TRIANGLE
        private var mToolTipColor: Int = Color.WHITE
        private var mToolTipWidth: Int = Utils.dpToPx(mContext, 6).roundToInt()
        private var mToolTipHeight: Int = Utils.dpToPx(mContext, 6).roundToInt()
        private var mToolTipOrientation: Orientation = Orientation.UP

        fun getToolTipShape(): ShapeType = mToolTipShape
        fun getToolTipColor(): Int = mToolTipColor
        fun getToolTipWidth(): Int = mToolTipWidth
        fun getToolTipHeight(): Int = mToolTipHeight
        fun getToolTipOrientation(): Orientation = mToolTipOrientation

        fun setConfig(config: CoachMarkConfig) {
            val toolTipBuilder = config.getCoachMarkToolTipBuilder()
            toolTipBuilder?.let {
                setToolTipColor(it.getToolTipColor())
                setToolTipHeight(it.getToolTipHeight())
                setToolTipWidth(it.getToolTipWidth())
                setToolTipOrientation(it.getToolTipOrientation())
                setToolTipShape(it.getToolTipShape())
            }
        }

        fun setToolTipShape(shape: ShapeType): Builder {
            mToolTipShape = shape
            return this
        }

        fun setToolTipColor(color: Int): Builder {
            mToolTipColor = color
            return this
        }

        fun setToolTipSize(w: Int, h: Int): Builder {
            mToolTipWidth = Utils.dpToPx(mContext, w).roundToInt()
            mToolTipHeight = Utils.dpToPx(mContext, h).roundToInt()
            return this
        }

        fun setToolTipWidth(w: Int): Builder {
            mToolTipWidth = Utils.dpToPx(mContext, w).roundToInt()
            return this
        }

        fun setToolTipHeight(h: Int): Builder {
            mToolTipHeight = Utils.dpToPx(mContext, h).roundToInt()
            return this
        }

        fun setToolTipOrientation(orientation: Orientation): Builder {
            mToolTipOrientation = orientation
            return this
        }

        fun build(): CoachMarkInfoToolTip? {
            return CoachMarkInfoToolTip(
                mContext,
                this
            )
        }
    }
}