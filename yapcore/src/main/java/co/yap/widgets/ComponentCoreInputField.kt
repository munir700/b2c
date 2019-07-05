package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.util.AttributeSet
import android.view.*
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.text.color
import androidx.databinding.DataBindingUtil
import androidx.databinding.InverseBindingListener
import androidx.databinding.ViewDataBinding
import co.yap.yapcore.BR
import co.yap.yapcore.CoreInPutVariable
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.custom_widget_edit_text.view.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("CustomViewStyleable")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class ComponentCoreInputField @JvmOverloads constructor(
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
    var PHONE_NUMBER_LENGTH: Int = 16
    var editText: EditText
    private lateinit var viewDataBinding: ViewDataBinding

    init {
        viewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_widget_edit_text, this, true)
//        viewDataBinding.setVariable(BR.coreInputFieldWidget, String)
//        viewDataBinding.setVariable(BR.coreInPutVariable, CoreInPutVariable)

        viewDataBinding.executePendingBindings()


//        LayoutInflater.from(context).inflate(R.layout.custom_widget_edit_text, this, true)


        editText = etInputField


        attrs?.let {
            typedArray = context.obtainStyledAttributes(it, R.styleable.ComponentCoreInputField, 0, 0)
            val title = resources.getText(
                typedArray
                    .getResourceId(R.styleable.ComponentCoreInputField_view_hint_input_field, R.string.empty_string)
            )
            inputType = typedArray.getInt(R.styleable.ComponentCoreInputField_view_input_type, inputType)


            val error = resources.getText(
                typedArray.getResourceId(
                    R.styleable.ComponentCoreInputField_view_error_input_field,
                    R.string.empty_string
                )
            )

            if (null != typedArray.getString(R.styleable.ComponentCoreInputField_view_input_text)) {

                textInput =
                    typedArray.getString(R.styleable.ComponentCoreInputField_view_input_text)
            }

            if (null != typedArray.getDrawable(R.styleable.ComponentCoreInputField_view_drawable_right)) {
                drawableRight = typedArray.getDrawable(R.styleable.ComponentCoreInputField_view_drawable_right)
                etInputField.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null)
            } else {
                drawableRight = null
            }

            if (null != typedArray.getDrawable(R.styleable.ComponentCoreInputField_view_drawable_left)) {
                drawableLeft = typedArray.getDrawable(R.styleable.ComponentCoreInputField_view_drawable_left)

                etInputField.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
            }

            if (null != drawableRight && null != drawableLeft) {
                etInputField.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)
            }

            if (!textInput.isEmpty()) {
                etInputField.setText(textInput)
            }

            etInputField.hint = title
            setViewInputType()
            if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal()
            typedArray.recycle()


            /* text paint styling */

            paintText.textAlign = Paint.Align.CENTER
            paintText.style = Paint.Style.FILL

            onKeyBoardDismissal()
        }

    }

    private fun setViewInputType() {
        when (inputType) {
            PHONE_INPUT_TYPE -> {
                etInputField.inputType = InputType.TYPE_CLASS_NUMBER
                setPhoneNumberField()
            }

            EMAIL_INPUT_TYPE -> {
                etInputField.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            }
        }
    }

    private fun setPhoneNumberField() {
        etInputField.setFilters(arrayOf<InputFilter>(InputFilter.LengthFilter(PHONE_NUMBER_LENGTH)))

        val builder = SpannableStringBuilder("")
        builder.color(color = R.color.greySoft) {
            append(countryCode)
        }

        etInputField.setText(builder)
        etInputField.setSelection(etInputField.text.length)
        disableTextSelection()

        etInputField.setCursorVisible(false)

        etInputField.setOnClickListener(OnClickListener { etInputField.setSelection(etInputField.getText().toString().length) })

        etInputField.setCursorVisible(true)
    }

    fun cursorPlacement() {
        etInputField.setOnClickListener(OnClickListener { etInputField.setSelection(etInputField.getText().toString().length) })
        etInputField.setCursorVisible(true)
    }

    private fun disableTextSelection() {
        etInputField.isLongClickable = false
        etInputField.setCustomSelectionActionModeCallback(object : ActionMode.Callback {
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
            etInputField.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)

        } else {
            etInputField.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null)
        }

    }

    fun setDrawableLeftIcon(drawable: Drawable) {
        drawableLeft = drawable

        if (null != drawableRight) {
            etInputField.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)

        } else {
            etInputField.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, null, null)
        }
    }

    fun setListener(listener: InverseBindingListener?) {
        if (listener != null) {
            etInputField.addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
                    override fun afterTextChanged(editable: Editable) {
                        listener.onChange()
                    }
                })
        }
    }

    fun setInputText(text: String) {
        etInputField.setText(text)
    }

    fun getInputText(): String {
        return etInputField.text.toString()

    }

    fun settingUIForError(error: String) {
        etInputField.setBackgroundResource(R.drawable.bg_round_error_layout)
        tvError.text = error
        tvError.visibility = View.VISIBLE
        setDrawableRightIcon(resources.getDrawable(R.drawable.invalid_name))

    }

    fun settingUIForNormal() {
        etInputField.setBackgroundResource(R.drawable.bg_round_edit_text)
        tvError.text = ""
        tvError.visibility = View.GONE
    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        viewWeight = w
        viewHeight = h
    }


    /* handle keyboard dismissal event */
    fun onKeyBoardDismissal() {

        etInputField.getViewTreeObserver().addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (etInputField.isFocused()) {
                        if (!keyboardShown(etInputField.getRootView())) {
                            editText.isActivated = false
                        } else {
                            editText.isActivated = true
                        }
                    }
                    return
                }
            })
    }

    private fun keyboardShown(rootView: View): Boolean {

        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }

    private fun getEditTextWatcher(editTextValue: String) {

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}
