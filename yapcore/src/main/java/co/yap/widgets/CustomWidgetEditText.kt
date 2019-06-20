package co.yap.widgets

import android.content.Context
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
//            val drawableEnd = resources?.getDrawable(
//                typedArray
//                    .getResourceId(
//                        R.styleable.view_input_field_view_right_drawable,
//                        R.drawable.ic_keyboard_arrow_left_black_24dp
//                    ), resources.newTheme()
//            ).toString()

            etEmail.hint = title
            if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal(error = error.toString())
            typedArray.recycle()
        }

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