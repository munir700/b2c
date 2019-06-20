package co.yap.widgets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.os.Build
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.custom_widget_edit_text.view.*


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CustomWidgetEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {

    var drawable: Drawable? = null
    private var drawablePositionType: Int = 0


    init {
        LayoutInflater.from(context).inflate(R.layout.custom_widget_edit_text, this, true)


        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.view_input_field, 0, 0)
            val title = resources.getText(
                typedArray
                    .getResourceId(R.styleable.view_input_field_view_hint_input_field, R.string.empty_string)
            )
            val error = resources.getText(
                typedArray.getResourceId(R.styleable.view_input_field_view_error_input_field, R.string.empty_string)
            )
            drawable = typedArray.getDrawable(
                R.styleable.view_input_field_view_drawable
            )
            drawablePositionType=typedArray.getInt(R.styleable.view_input_field_view_drawable_position, 1)
            etEmail.hint = title
            if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal(error = error.toString())
            typedArray.recycle()
        }

    }

    fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null

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


}