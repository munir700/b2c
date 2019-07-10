package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.*
import android.view.View.OnClickListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.text.color
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
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
    var imeiActionType: Int = 1
    var IME_NEXT: Int = 2
    var PHONE_INPUT_TYPE: Int = 1
    var EMAIL_INPUT_TYPE: Int = 2
    var PHONE_NUMBER_LENGTH: Int = 16
    var editText: EditText
    var checkFocusChange: Boolean = false
    private lateinit var viewDataBinding: ViewDataBinding

    init {
        viewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_widget_edit_text, this, true)
        viewDataBinding.executePendingBindings()
        editText = etInputField


        attrs?.let {
            typedArray = context.obtainStyledAttributes(it, R.styleable.CoreInputField, 0, 0)
            val title = resources.getText(
                typedArray
                    .getResourceId(R.styleable.CoreInputField_view_hint_input_field, R.string.empty_string)
            )

            inputType = typedArray.getInt(R.styleable.CoreInputField_view_input_type, inputType)
            checkFocusChange = typedArray.getBoolean(R.styleable.CoreInputField_view_focusable, checkFocusChange)
            imeiActionType = typedArray.getInt(R.styleable.CoreInputField_view_input_text_imei_actions, imeiActionType)

            val error = resources.getText(
                typedArray.getResourceId(
                    R.styleable.CoreInputField_view_error_input_field,
                    R.string.empty_string
                )
            )

            if (null != typedArray.getString(R.styleable.CoreInputField_view_input_text)) {

                textInput =
                    typedArray.getString(R.styleable.CoreInputField_view_input_text)
            }

            if (null != typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_right)) {
                drawableRight = typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_right)
                etInputField.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null)
            } else {
                drawableRight = null
            }

            if (null != typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_left)) {
                drawableLeft = typedArray.getDrawable(R.styleable.CoreInputField_view_drawable_left)

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
            setImeiActionsType()
            if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal()
            typedArray.recycle()


            /* text paint styling */

            paintText.textAlign = Paint.Align.CENTER
            paintText.style = Paint.Style.FILL

            etInputField.isFocusable
            onKeyBoardDismissal(true)
            animteKeyboardDismissal()
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

    private fun setImeiActionsType() {
        when (imeiActionType) {
            IME_NEXT -> {
                etInputField.imeOptions = EditorInfo.IME_ACTION_NEXT
                checkFocusChange = true

//                   etInputField.setOnEditorActionListener( { textView, action, event ->
//                        var handled = false
//                        if (action == EditorInfo.IME_ACTION_NEXT) {
//                            var listener = object : OnFocusChangeListener {
//                                //            internal var layout_nama_pp = findViewById<View>(co.yap.yapcore.R.id.layout_nama_pp) as LinearLayout
//                                override fun onFocusChange(v: View, hasFocus: Boolean) {
//                                    if (!hasFocus) {
//                                        etInputField.isActivated = false
//                                    } else {
////                    editText.isActivated = true
//                                        etInputField.getViewTreeObserver().addOnGlobalLayoutListener(
//                                            object : ViewTreeObserver.OnGlobalLayoutListener {
//                                                override fun onGlobalLayout() {
////                    if (etInputField.isFocused()) {
//                                                    if (!keyboardShown(etInputField.getRootView())) {
//                                                        etInputField.requestFocus()
//                                                        etInputField.isActivated = false
//                                                    } else {
//                                                        etInputField.isActivated = true
//                                                    }
////                    }
//                                                    return
//                                                }
//                                            })
//                                    }
//                                }
//                            }
//
//                            etInputField.setOnFocusChangeListener(listener)
//                            handled = true
//                        }
//                        handled
//                    })


            }
            else -> {
                etInputField.imeOptions = EditorInfo.IME_ACTION_DONE
                onKeyBoardDismissal(true)

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
    fun onKeyBoardDismissal(check: Boolean) {

        if (checkFocusChange) {
            var listener = object : OnFocusChangeListener {
                override fun onFocusChange(v: View, hasFocus: Boolean) {
                    if (!hasFocus) {
                        etInputField.isActivated = false
                    } else {
                        etInputField.isActivated = true
                    }
                }
            }

            etInputField.setOnFocusChangeListener(listener)
        } else {

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
    }

    /* animate down keyboard */
    fun animteKeyboardDismissal() {

        val view = this.editText
        view?.let { v ->
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.let { it.hideSoftInputFromWindow(v.windowToken, 0) }
        }
    }

    fun keyboardShown(rootView: View): Boolean {

        val softKeyboardHeight = 100
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics
        val heightDiff = rootView.bottom - r.bottom
        return heightDiff > softKeyboardHeight * dm.density
    }
}
