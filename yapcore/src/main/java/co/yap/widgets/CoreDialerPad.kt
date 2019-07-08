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

   init {
        LayoutInflater.from(context).inflate(R.layout.core_dialer_pad, this, true)
        orientation = VERTICAL
        editText=etPassCodeText

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CoreDialerPad, 0, 0)
            val dialerType = typedArray.getInt(R.styleable.CoreDialerPad_dialer_pass_code, 0)
            val dialerMaxLength = typedArray.getInt(R.styleable.CoreDialerPad_dialer_max_length, 6)
            etPassCodeText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(dialerMaxLength))
           /* val error = resources.getText(
                typedArray.getResourceId(R.styleable.CoreInputField_view_error_input_field, R.string.empty_string)
            )*/
              if (dialerType == 1) performPassCode()

            button1.setOnClickListener {
                etPassCodeText.append("1")
            }
            button2.setOnClickListener {
                etPassCodeText.append("2")
            }
            button3.setOnClickListener {
                etPassCodeText.append("3")
            }
            button4.setOnClickListener {
                etPassCodeText.append("4")
            }
            button5.setOnClickListener {
                etPassCodeText.append("5")
            }
            button6.setOnClickListener {
                etPassCodeText.append("6")
            }
            button7.setOnClickListener {
                etPassCodeText.append("7")
            }
            button8.setOnClickListener {
                etPassCodeText.append("8")
            }
            button9.setOnClickListener {
                etPassCodeText.append("9")
            }
            button0.setOnClickListener {
                etPassCodeText.append("0")
            }
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

    fun settingUIForNormal() {
        tvError.visibility = View.INVISIBLE
    }
    fun performPassCode(){
        etPassCodeText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
        etPassCodeText.textSize= resources.getDimension(R.dimen.text_size_h1) //R.dimen.margin_xxl.toFloat()

//        etPassCodeText.inputType=InputType.TYPE_NUMBER_VARIATION_PASSWORD
/*        etPassCodeText.inputType=InputType.TYPE_TEXT_VARIATION_PASSWORD
        etPassCodeText.inputType=InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        etPassCodeText.inputType=InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD*/
        btnFingerPrint.setImageDrawable(resources.getDrawable(R.drawable.ic_fingerprint_purple,null))
    }
}




