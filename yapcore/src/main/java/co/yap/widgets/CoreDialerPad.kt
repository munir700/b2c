package co.yap.widgets

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import android.widget.LinearLayout
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.core_dialer_pad.view.*


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("Recycle")
class CoreDialerPad @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    var editText: EditText
    var onButtonClickListener: View.OnClickListener? = null

    private val onClickListener: View.OnClickListener = OnClickListener {
        if (it.id == R.id.button1) etPassCodeText.append("1")
        if (it.id == R.id.button2) etPassCodeText.append("2")
        if (it.id == R.id.button3) etPassCodeText.append("3")
        if (it.id == R.id.button4) etPassCodeText.append("4")
        if (it.id == R.id.button5) etPassCodeText.append("5")
        if (it.id == R.id.button6) etPassCodeText.append("6")
        if (it.id == R.id.button7) etPassCodeText.append("7")
        if (it.id == R.id.button8) etPassCodeText.append("8")
        if (it.id == R.id.button9) etPassCodeText.append("9")
        if (it.id == R.id.button0) etPassCodeText.append("0")
        // if (it.id == R.id.btnFingerPrint) etPassCodeText.append("0")

        onButtonClickListener?.onClick(it)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.core_dialer_pad, this, true)
        orientation = VERTICAL
        editText = etPassCodeText

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CoreDialerPad, 0, 0)
            val dialerType = typedArray.getInt(R.styleable.CoreDialerPad_dialer_pass_code, 0)
            val dialerMaxLength = typedArray.getInt(R.styleable.CoreDialerPad_dialer_max_length, 6)
            etPassCodeText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(dialerMaxLength))
            /* val error = resources.getText(
                 typedArray.getResourceId(R.styleable.CoreInputField_view_error_input_field, R.string.empty_string)
             )*/
            if (dialerType == 1) performPassCode()

            button1.setOnClickListener(onClickListener)
            button2.setOnClickListener(onClickListener)
            button3.setOnClickListener(onClickListener)
            button4.setOnClickListener(onClickListener)
            button5.setOnClickListener(onClickListener)
            button6.setOnClickListener(onClickListener)
            button7.setOnClickListener(onClickListener)
            button8.setOnClickListener(onClickListener)
            button9.setOnClickListener(onClickListener)
            button0.setOnClickListener(onClickListener)
            btnFingerPrint.setOnClickListener(onClickListener)
            // if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal()
//            typedArray.recycle()
        }
        buttonRemove.setOnClickListener {
            val length = etPassCodeText.length()
            if (length > 0) etPassCodeText.text.delete(length - 1, length)
        }

    }

    fun getText(): String {
        return etPassCodeText.text.toString()
    }

    fun settingUIForError(error: String) {
        tvError.visibility = View.VISIBLE
        tvError.text = error
    }

    fun showFingerprintView() {
        btnFingerPrint.visibility = View.VISIBLE
    }

    fun hideFingerprintView() {
        btnFingerPrint.visibility = View.INVISIBLE
    }


    fun settingUIForNormal() {
        tvError.visibility = View.INVISIBLE
    }

    fun performPassCode() {
        etPassCodeText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
        etPassCodeText.textSize = resources.getDimension(R.dimen.text_size_h1) //R.dimen.margin_xxl.toFloat()

//        etPassCodeText.inputType=InputType.TYPE_NUMBER_VARIATION_PASSWORD
/*        etPassCodeText.inputType=InputType.TYPE_TEXT_VARIATION_PASSWORD
        etPassCodeText.inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        etPassCodeText.inputType=InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD*/
        btnFingerPrint.setImageDrawable(resources.getDrawable(R.drawable.ic_fingerprint_purple, null))
    }
}





