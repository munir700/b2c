package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.*
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.text.color
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
    private var paintText: Paint = Paint()
    private var viewWeight: Int = 0
    private var viewHeight: Int = 0
    private var textInput: String = ""
    public var countryCode: String = "+971 "
    lateinit var typedArray: TypedArray
    var inputType: Int = 0
    var PHONE_INPUT_TYPE: Int = 1
    var EMAIL_INPUT_TYPE: Int = 2
    var PHONE_NUMBER_LENGTH: Int = 13
    lateinit var editText: EditText

    init {

        LayoutInflater.from(context).inflate(R.layout.custom_widget_edit_text, this, true)
        editText = etEmail

        attrs?.let {
            typedArray = context.obtainStyledAttributes(it, R.styleable.CoreInputField, 0, 0)
            val title = resources.getText(
                typedArray
                    .getResourceId(R.styleable.CoreInputField_view_hint_input_field, R.string.empty_string)
            )
            inputType = typedArray.getInt(R.styleable.CoreInputField_view_input_type, inputType)


            val error = resources.getText(
                typedArray.getResourceId(R.styleable.CoreInputField_view_error_input_field, R.string.empty_string)
            )

            if (null != typedArray.getString(R.styleable.CoreInputField_view_input_text)) {

                textInput =
                    typedArray.getString(R.styleable.CoreInputField_view_input_text)
            }

            if (null != typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_right)) {
                drawableRight = typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_right)
                etEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null)
            } else {
                drawableRight = null
            }

            if (null != typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_left)) {
                drawableLeft = typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_left)

                etEmail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
            }

            if (null != drawableRight && null != drawableLeft) {
                etEmail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)
            }

            if (!textInput.isEmpty()) {
                etEmail.setText(textInput)
            }

            etEmail.hint = title
            setViewInputType()
            if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal()
            typedArray.recycle()


            /* text paint styling */

            paintText.textAlign = Paint.Align.CENTER
            paintText.style = Paint.Style.FILL

        }

    }

    private fun setViewInputType() {
        when (inputType) {
            PHONE_INPUT_TYPE -> {
                etEmail.inputType = InputType.TYPE_CLASS_PHONE
                setPhoneNumberField()
            }

            EMAIL_INPUT_TYPE -> {
                etEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
        }
    }

    private fun setPhoneNumberField() {
        etEmail.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(PHONE_NUMBER_LENGTH)))

        val builder = SpannableStringBuilder("")
        builder.color(color = R.color.greySoft) {
            append(countryCode)
        }

        etEmail.setText(builder)
        etEmail.setSelection(etEmail.text.length)
        disableTextSelection()

        etEmail.setCursorVisible(false)

        etEmail.setOnClickListener(OnClickListener { etEmail.setSelection(etEmail.getText().toString().length) })

        etEmail.setCursorVisible(true)
    }

    fun cursorPlacement() {
        etEmail.setOnClickListener(OnClickListener { etEmail.setSelection(etEmail.getText().toString().length) })
        etEmail.setCursorVisible(true)
    }

    private fun disableTextSelection() {
        etEmail.isLongClickable = false
        etEmail.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return false
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun setDrawableRightIcon(drawable: Drawable?) {
        drawableRight = drawable
        if (null != drawableLeft) {
            etEmail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)

        } else {
            etEmail.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null)

        }
    }

    fun setDrawableLeftIcon(drawable: Drawable) {
        drawableLeft = drawable

        if (null != drawableRight) {
            etEmail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)

        } else {
            etEmail.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
        }
    }

    fun setview_input_text(text: String) {
        etEmail.setText(text)

    }

    fun getview_input_text(): String? {
        return etEmail.text.toString()

    }

    fun settingUIForError(error: String) {
        etEmail.setBackgroundResource(R.drawable.bg_round_error_layout)
        tvError.text = error
        tvError.visibility = View.VISIBLE
        setDrawableRightIcon(resources.getDrawable(R.drawable.invalid_name))

    }

    fun settingUIForNormal() {
        etEmail.setBackgroundResource(R.drawable.bg_round_edit_text)
        tvError.text = ""
        tvError.visibility = View.GONE
    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        viewWeight = w
        viewHeight = h
    }
}
