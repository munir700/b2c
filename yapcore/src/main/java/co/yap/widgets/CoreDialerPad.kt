package co.yap.widgets

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
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
    var list: ArrayList<Int> = ArrayList()
    var dialerType = 0
    val animShake = AnimationUtils.loadAnimation(context, R.anim.shake)

    init {
        LayoutInflater.from(context).inflate(R.layout.core_dialer_pad, this, true)
        orientation = VERTICAL
        editText = etPassCodeText

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CoreDialerPad, 0, 0)
            dialerType = typedArray.getInt(R.styleable.CoreDialerPad_dialer_pass_code, 0)
            val dialerMaxLength = typedArray.getInt(R.styleable.CoreDialerPad_dialer_max_length, 6)
            etPassCodeText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(dialerMaxLength))
            /* val error = resources.getText(
                 typedArray.getResourceId(R.styleable.CoreInputField_view_error_input_field, R.string.empty_string)
             )*/
            if (dialerType == 1) performPassCode()

            button1.setOnClickListener {
                etPassCodeText.append("1")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }

            }
            button2.setOnClickListener {
                etPassCodeText.append("2")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button3.setOnClickListener {
                etPassCodeText.append("3")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button4.setOnClickListener {
                etPassCodeText.append("4")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button5.setOnClickListener {
                etPassCodeText.append("5")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button6.setOnClickListener {
                etPassCodeText.append("6")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button7.setOnClickListener {
                etPassCodeText.append("7")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button8.setOnClickListener {
                etPassCodeText.append("8")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button9.setOnClickListener {
                etPassCodeText.append("9")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            button0.setOnClickListener {
                etPassCodeText.append("0")
                if (dialerType == 1) {
                    addListSizeForPasscode()
                }
            }
            // if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal()
//            typedArray.recycle()

            buttonRemove.setOnClickListener {
                if (dialerType == 1) {
                    removePasscodeFromList()
                } else {
                    val length = etPassCodeText.length()
                    if (length > 0) etPassCodeText.text.delete(length - 1, length)
                }
            }
        }

    }

    fun getText(): String {
        return etPassCodeText.text.toString()
    }

    fun startAnimation() {
        llPasscode.startAnimation(animShake)
    }

    fun settingUIForError(error: String) {
        tvError.visibility = View.VISIBLE
        tvError.text = error
    }

    fun settingUIForNormal() {
        tvError.visibility = View.INVISIBLE
    }

    fun performPassCode() {
        // etPassCodeText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD
        etPassCodeText.textSize = resources.getDimension(R.dimen.text_size_h1) //R.dimen.margin_xxl.toFloat()
        etPassCodeText.visibility = View.GONE
        llPasscode.visibility = View.VISIBLE
        btnFingerPrint.setImageDrawable(resources.getDrawable(R.drawable.ic_fingerprint_purple, null))
    }

    private fun addListSizeForPasscode() {
        if (list.size < 6) {
            list.add(1)
        }
        if (list.size == 1) {
            ivOne.visibility = View.VISIBLE
        } else if (list.size == 2) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
        } else if (list.size == 3) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
        } else if (list.size == 4) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
            ivFour.visibility = View.VISIBLE
        } else if (list.size == 5) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
            ivFour.visibility = View.VISIBLE
            ivFive.visibility = View.VISIBLE
        } else if (list.size == 6) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
            ivFour.visibility = View.VISIBLE
            ivFive.visibility = View.VISIBLE
            ivSix.visibility = View.VISIBLE
        }
    }

    fun removePasscodeFromList() {
        list.remove(1)
        if (list.size == 0) {
            ivOne.visibility = View.GONE
            ivTwo.visibility = View.GONE
            ivThree.visibility = View.GONE
            ivFour.visibility = View.GONE
            ivFive.visibility = View.GONE
            ivSix.visibility = View.GONE
        } else if (list.size == 1) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.GONE
            ivThree.visibility = View.GONE
            ivFour.visibility = View.GONE
            ivFive.visibility = View.GONE
            ivSix.visibility = View.GONE
        } else if (list.size == 2) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.GONE
            ivFour.visibility = View.GONE
            ivFive.visibility = View.GONE
            ivSix.visibility = View.GONE
        } else if (list.size == 3) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
            ivFour.visibility = View.GONE
            ivFive.visibility = View.GONE
            ivSix.visibility = View.GONE
        } else if (list.size == 4) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
            ivFour.visibility = View.VISIBLE
            ivFive.visibility = View.GONE
            ivSix.visibility = View.GONE
        } else if (list.size == 5) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
            ivFour.visibility = View.VISIBLE
            ivFive.visibility = View.VISIBLE
            ivSix.visibility = View.GONE
        } else if (list.size == 6) {
            ivOne.visibility = View.VISIBLE
            ivTwo.visibility = View.VISIBLE
            ivThree.visibility = View.VISIBLE
            ivFour.visibility = View.VISIBLE
            ivFive.visibility = View.VISIBLE
            ivSix.visibility = View.VISIBLE
        }

    }
}




