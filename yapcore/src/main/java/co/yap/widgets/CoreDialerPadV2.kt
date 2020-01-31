package co.yap.widgets

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import co.yap.yapcore.R


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("Recycle")
class CoreDialerPadV2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {

    var etPassCodeText: EditText? = null
    var list: ArrayList<Int> = ArrayList()
    var dialerType = 0
    var showDialerPassCodeView: Boolean = true
        set(value) {
            field = value
            view.findViewById<ConstraintLayout>(R.id.clPassCodeViews).visibility =
                if (value) View.VISIBLE else View.GONE
        }
    var dialerMaxLength = 6
    val animShake = AnimationUtils.loadAnimation(context, R.anim.shake)
    //var onButtonClickListener: OnClickListener? = null
    private var inputEditText: EditText? = null
    private var listener: NumberKeyboardListener? = null
    private var view: View =
        inflate(context, R.layout.core_dialer_pad_v2, null)

    private val onClickListener: OnClickListener = OnClickListener {
        if (it.id == R.id.button1) {
            etPassCodeText?.append("1")
            inputEditText?.append("1")
            listener?.onNumberClicked(1, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }

        if (it.id == R.id.button2) {
            etPassCodeText?.append("2")
            inputEditText?.append("2")
            listener?.onNumberClicked(2, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button3) {
            etPassCodeText?.append("3")
            inputEditText?.append("3")
            listener?.onNumberClicked(3, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button4) {
            etPassCodeText?.append("4")
            inputEditText?.append("4")
            listener?.onNumberClicked(4, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button5) {
            etPassCodeText?.append("5")
            inputEditText?.append("5")
            listener?.onNumberClicked(5, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button6) {
            etPassCodeText?.append("6")
            inputEditText?.append("6")
            listener?.onNumberClicked(6, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button7) {

            etPassCodeText?.append("7")
            inputEditText?.append("7")
            listener?.onNumberClicked(7, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button8) {

            etPassCodeText?.append("8")
            inputEditText?.append("8")
            listener?.onNumberClicked(8, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button9) {

            etPassCodeText?.append("9")
            inputEditText?.append("9")
            listener?.onNumberClicked(9, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.button0) {

            etPassCodeText?.append("0")
            inputEditText?.append("0")
            listener?.onNumberClicked(0, etPassCodeText?.text.toString())
            if (dialerType == 1) {
                addListSizeForPasscode(dialerMaxLength)
            }
        }
        if (it.id == R.id.btnFingerPrint) {
            listener?.onLeftButtonClicked()
        }
        //onButtonClickListener?.onClick(it)
    }

    init {
        orientation = VERTICAL
        addView(view)
        etPassCodeText = view.findViewById(R.id.etPassCodeText)
        // editText = etPassCodeText


        attrs?.let { it ->
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CoreDialerPad, 0, 0)
            dialerType = typedArray.getInt(R.styleable.CoreDialerPad_dialer_pass_code, 0)
            dialerMaxLength = typedArray.getInt(R.styleable.CoreDialerPad_dialer_max_length, 6)
            showDialerPassCodeView =
                typedArray.getBoolean(R.styleable.CoreDialerPad_show_dialer_pass_code, true)
            etPassCodeText?.filters =
                arrayOf<InputFilter>(InputFilter.LengthFilter(dialerMaxLength))

            if (dialerType == 1) performPassCode()
            view.findViewById<View>(R.id.button1).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button2).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button3).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button4).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button5).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button6).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button7).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button8).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button9).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.button0).setOnClickListener(onClickListener)
            view.findViewById<View>(R.id.btnFingerPrint).setOnClickListener(onClickListener)

            view.findViewById<ImageView>(R.id.buttonRemove).setOnClickListener {
                removePasscodeFromList()
                val length = etPassCodeText?.length()!!
                if (length > 0) etPassCodeText?.text?.delete(length - 1, length)
                inputEditText?.let {
                    if (it.length() > 0) it.text.delete(it.length() - 1, it.length())
                }
                listener?.onRightButtonClicked()
            }
        }
    }

    fun setInPutEditText(editText: EditText?) {
        if (!showDialerPassCodeView) {
            inputEditText = editText
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    fun getText(): String {
        return etPassCodeText?.text.toString()
    }

    private fun getInputEditText(): String {
        return inputEditText?.text.toString()
    }

    fun updateDialerLength(length: Int) {
        dialerMaxLength = length
        etPassCodeText?.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(dialerMaxLength))
    }


    fun startAnimation() {
        view.findViewById<LinearLayout>(R.id.llPasscode).startAnimation(animShake)
    }

    fun startAnimationDigits() {
        etPassCodeText?.startAnimation(animShake)
    }

    fun settingUIForError(error: String) {
        view.findViewById<TextView>(R.id.tvError).visibility = View.VISIBLE
        view.findViewById<TextView>(R.id.tvError).text = error
    }

    fun showFingerprintView() {
        view.findViewById<ImageButton>(R.id.btnFingerPrint).visibility = View.VISIBLE
    }

    fun hideFingerprintView() {
        view.findViewById<ImageButton>(R.id.btnFingerPrint).visibility = View.INVISIBLE
    }


    fun settingUIForNormal() {
        view.findViewById<TextView>(R.id.tvError).visibility = View.INVISIBLE
    }

    fun performPassCode() {
        etPassCodeText?.textSize =
            resources.getDimension(R.dimen.text_size_h1) //R.dimen.margin_xxl.toFloat()
        etPassCodeText?.visibility = View.GONE
        view.findViewById<LinearLayout>(R.id.llPasscode).visibility = View.VISIBLE
        view.findViewById<ImageButton>(R.id.btnFingerPrint).setImageDrawable(
            resources.getDrawable(
                R.drawable.ic_fingerprint_purple,
                null
            )
        )
    }

    fun upDatedDialerPad(passcode: String? = null) {
        passcode?.let {
            etPassCodeText?.setText(it)
        }
        if (passcode == null) {
            updateDialerPadValues(etPassCodeText?.length() ?: 0)
        } else {
            updateDialerPadValues(passcode.length)
        }
    }

    private fun addListSizeForPasscode(dialerLength: Int) {
        if (list.size < dialerLength) {
            list.add(1)
        }
        if (list.size == 1) {
            view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
        } else if (list.size == 2) {
            view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
        } else if (list.size == 3) {
            view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
        } else if (list.size == 4) {
            view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
        } else if (list.size == 5) {
            view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivFive).visibility = View.VISIBLE
        } else if (list.size == 6) {
            view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivFive).visibility = View.VISIBLE
            view.findViewById<ImageView>(R.id.ivSix).visibility = View.VISIBLE
        }
    }

    private fun updateDialerPadValues(dialerLength: Int) {

        when (dialerLength) {
            4 -> {
                list.add(1)
                list.add(1)
                list.add(1)
                list.add(1)
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
            }
            5 -> {
                list.add(1)
                list.add(1)
                list.add(1)
                list.add(1)
                list.add(1)
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.VISIBLE
            }
            6 -> {
                list.add(1)
                list.add(1)
                list.add(1)
                list.add(1)
                list.add(1)
                list.add(1)
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.VISIBLE
            }
        }
    }

    private fun removePasscodeFromList() {
        if (dialerType == 1) {
            list.remove(1)
            if (list.size == 0) {
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.GONE
            } else if (list.size == 1) {
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.GONE
            } else if (list.size == 2) {
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.GONE
            } else if (list.size == 3) {
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.GONE
            } else if (list.size == 4) {
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.GONE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.GONE
            } else if (list.size == 5) {
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.GONE
            } else if (list.size == 6) {
                view.findViewById<ImageView>(R.id.ivOne).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivTwo).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivThree).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFour).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivFive).visibility = View.VISIBLE
                view.findViewById<ImageView>(R.id.ivSix).visibility = View.VISIBLE
            }
        }
    }

    /**
     * Sets keyboard listener.
     */
    fun setNumberKeyboardListener(listener: NumberKeyboardListener?) {
        this.listener = listener
    }
}

/**
 * Enables to listen keyboard events.
 */
interface NumberKeyboardListener {

    /**
     * Invoked when a number key is clicked.
     */
    fun onNumberClicked(number: Int, text: String){}

    /**
     * Invoked when the left auxiliary button is clicked.
     */
    fun onLeftButtonClicked(){}

    /**
     * Invoked when the right auxiliary button is clicked.
     */
    fun onRightButtonClicked(){}
}





