package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.custom_widget_edit_text.view.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("CustomViewStyleable")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CoreInputField @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    var drawableRight: Drawable? = null
    var drawableLeft: Drawable? = null
    private var drawablePositionType: Int = 1
    private var paintText: Paint = Paint()
    private var DRAWABLE_RIGHT: Int = 1
    private var DRAWABLE_LEFT: Int = 0
    private var DRAWABLES: Int = 2
    lateinit var bitmapIconRight: Bitmap
    lateinit var bitmapIconLeft: Bitmap

    private var viewWeight: Int = 0
    private var viewHeight: Int = 0


    private var defaultDrawablePaddingLeft: Float = 9.5f
    private var defaultDrawablePaddingRight: Float = 1.1f
    private var defaultDrawablePaddingTop: Float = 5.5f

    private var drawablePaddingLeft: Float = 9.5f
    private var drawablePaddingRight: Float = 1.1f
    private var drawablePaddingTop: Float = 5.5f

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_widget_edit_text, this, true)


        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CoreInputField, 0, 0)
            val title = resources.getText(
                typedArray
                    .getResourceId(R.styleable.CoreInputField_view_hint_input_field, R.string.empty_string)
            )
            val error = resources.getText(
                typedArray.getResourceId(R.styleable.CoreInputField_view_error_input_field, R.string.empty_string)
            )

            if (null != typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_right)) {
                drawableRight = typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_right)
                drawablePositionType=1
            }else{
                drawableRight = null

            }
            if (null != typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_left)) {
                drawableLeft = typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_left)
                drawablePositionType=0
            }else{
                drawableLeft=null
            }
            drawablePaddingLeft =
                typedArray.getFloat(R.styleable.CoreInputField_view_drawable_padding_left, defaultDrawablePaddingLeft)
            drawablePaddingRight = typedArray.getFloat(
                R.styleable.CoreInputField_view_drawable_padding_right,
                defaultDrawablePaddingRight
            )
            drawablePaddingTop =
                typedArray.getFloat(R.styleable.CoreInputField_view_drawable_padding_top, defaultDrawablePaddingTop)



            etEmail.hint = title
            if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal(error = error.toString())
            typedArray.recycle()


            /* text paint styling */

            paintText.textAlign = Paint.Align.CENTER
            paintText.style = Paint.Style.FILL

        }

    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        val bitmap: Bitmap?

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

    private fun settingUIForError(error: String) {
        etEmail.setBackgroundResource(R.drawable.bg_round_error_layout)
        tvError.text = error
    }

    private fun settingUIForNormal(error: String) {
        etEmail.setBackgroundResource(R.drawable.bg_round_edit_text)
        tvError.text = error
    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        viewWeight = w
        viewHeight = h
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
var checkNonEmptyDrawable=false;
        if(null!= drawableRight && null!= drawableLeft){
            drawablePositionType=DRAWABLES
        }
        if (null!=drawableLeft){
            bitmapIconLeft = drawableLeft?.let { this.drawableToBitmap(it) }!!
            checkNonEmptyDrawable=true
        }
        if (null!=drawableRight){
            bitmapIconRight = drawableRight?.let { this.drawableToBitmap(it) }!!
            checkNonEmptyDrawable=true
        }
        if (checkNonEmptyDrawable) {



            when (drawablePositionType) {
                DRAWABLE_LEFT ->
                canvas?.drawBitmap(
                    bitmapIconLeft,
                    (viewWeight / drawablePaddingLeft),    //position from left (float value)
                    (viewHeight / drawablePaddingTop),     // set y-position of drawableRight left from top (float value)
                    paintText
                )
//
                DRAWABLE_RIGHT ->
                canvas?.drawBitmap(
                    bitmapIconRight,
                    (viewWeight / drawablePaddingRight),       //position from left (float value)
                    (viewHeight / drawablePaddingTop),         // set y-position of drawableRight right (float value)
                    paintText
                )
                DRAWABLES -> {
                    canvas?.drawBitmap(
                        bitmapIconRight,
                        (viewWeight / drawablePaddingRight),       //position from left (float value)
                        (viewHeight / drawablePaddingTop),         // set y-position of drawableRight right (float value)
                        paintText
                    )

                    canvas?.drawBitmap(
                        bitmapIconLeft,
                        (viewWeight / drawablePaddingLeft),    //position from left (float value)
                        (viewHeight / drawablePaddingTop),     // set y-position of drawableRight left from top (float value)
                        paintText
                    )
                }
            }
        }

//        if (null != drawableLeft) {
//            bitmapIconLeft = drawableLeft?.let { this.drawableToBitmap(it) }!!
//
//
////            when (drawablePositionType) {
////                DRAWABLE_LEFT ->
//            canvas?.drawBitmap(
//                    bitmapIconLeft,
//                    (viewWeight / drawablePaddingLeft),    //position from left (float value)
//                    (viewHeight / drawablePaddingTop),     // set y-position of drawableLeft left from top (float value)
//                    paintText
//                )
//
////                DRAWABLE_RIGHT ->
//            canvas?.drawBitmap(
//                    bitmapIconLeft,
//                    (viewWeight / drawablePaddingRight),       //position from left (float value)
//                    (viewHeight / drawablePaddingTop),         // set y-position of drawableLeft right (float value)
//                    paintText
//                )
////                else ->
////                    canvas?.drawBitmap(
////                        bitmapIconLeft,
////                        (viewWeight / drawablePaddingTop),     //position from left (float value)
////                        (viewHeight / drawablePaddingTop),     // set y-position of drawableLeft right (float value)
////                        paintText
////                    )
////            }
//        }
    }

}