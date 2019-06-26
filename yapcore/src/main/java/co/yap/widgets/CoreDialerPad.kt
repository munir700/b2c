package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.text.InputFilter
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.view.marginEnd
import androidx.databinding.Observable
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.core_dialer_pad.view.*
import kotlin.properties.Delegates


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("Recycle")
class CoreDialerPad constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int,
    defStyleRes: Int
) : LinearLayout(context, attrs) {

    var passCodes: ArrayList<View> = arrayListOf()

    var dots: String = ""

    constructor(context: Context) : this(context, null, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : this(context, attrs, defStyle, 0)

    init {
        LayoutInflater.from(context).inflate(R.layout.core_dialer_pad, this, true)
        orientation = VERTICAL

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CoreDialerPad, 0, 0)
            val dialerType = typedArray.getInt(R.styleable.CoreDialerPad_dialer_pass_code, 1)
            val dialerMaxLength = typedArray.getInt(R.styleable.CoreDialerPad_dialer_max_length, 6)
            etPassCodeText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(dialerMaxLength))
            //  if (dialerType == 1) performDefaultPassCode() else performPassCode()

            /*  (1..dialerMaxLength).forEach {
                  val view? = View(context, attrs)=null
                  //val view: View = View(context, attrs) // create new view of height/widht with background unfilled
                  view.layoutParams.height = 14
                  view.layoutParams.width = 14
                  view.marginEnd.plus(20)
                  view.background = resources.getDrawable(R.drawable.passcode_empty_circle, null)
                  passCodes.add(view)
              }
              llPasscode.removeAllViews()
              for(view: View in passCodes) llPasscode.addView(view)*/


            // for loop (1..limit) -> check if it is not filled then fill it.
//            for (view: View in passCodes) {
//                if (view.background==resources.getDrawable(R.drawable.passcode_empty_circle))
//                    view.background=resources.getDrawable(R.drawable.passcode_empty_circle) else view.background=resources.getDrawable(R.drawable.passcode_fill_circle)
//            }
            button1.setOnClickListener {
                if (dialerType == 1) dots.plus("1") else etPassCodeText.append("1")
                onTextAdded()
            }
            button2.setOnClickListener {
                if (dialerType == 1) dots.plus("2") else etPassCodeText.append("2")
                onTextAdded()
            }
            button3.setOnClickListener {
                if (dialerType == 1) dots.plus("3") else etPassCodeText.append("3")
                onTextAdded()
            }
            button4.setOnClickListener {
                if (dialerType == 1) dots.plus("4") else etPassCodeText.append("4")
                onTextAdded()
            }
            button5.setOnClickListener {
                if (dialerType == 1) dots.plus("5") else etPassCodeText.append("5")
                onTextAdded()
            }
            button6.setOnClickListener {
                if (dialerType == 1) dots.plus("6") else etPassCodeText.append("6")
                onTextAdded()
            }
            button7.setOnClickListener {
                if (dialerType == 1) dots.plus("7") else etPassCodeText.append("7")
                onTextAdded()
            }
            button8.setOnClickListener {
                if (dialerType == 1) dots.plus("8") else etPassCodeText.append("8")
                onTextAdded()
            }
            button9.setOnClickListener {
                if (dialerType == 1) dots.plus("9") else etPassCodeText.append("9")
                onTextAdded()
            }
            button0.setOnClickListener {
                if (dialerType == 1) dots.plus("0") else etPassCodeText.append("0")
                onTextAdded()
            }
        }
        buttonRemove.setOnClickListener {
            val length = etPassCodeText.length()
            if (length > 0) etPassCodeText.text.delete(length - 1, length)
            // lop through the container
            // llPasscode.childCount
//            val v = llPasscode.getChildAt(i) as View
//            if (v.background !is )v.setBackgroundResource()
        }
    }

    /*val listener: OnClickListener = OnClickListener {
        if (it.id == R.id.button1) dots.plus("1")
    }*/

    private fun onTextUpdate(value: String) {
//        passCode1.background=resources.getDrawable(R.drawable.passcode_fill_circle,null)
        //etPassCodeText.setText(value)
    }


    private fun onTextAdded() {
        removeAllDots()
        fillDots(dots.length)
    }


    private fun onTextRemoved() {
        removeAllDots()
        fillDots(dots.length)
    }

    private fun removeAllDots() {
        for (view: View in llPasscode.children) {
            view.setBackgroundResource(R.drawable.passcode_empty_circle)
        }
    }

    private fun fillDots(count: Int) {
        (1..count).map {
            if (it < llPasscode.childCount) {
                val view: View = llPasscode.getChildAt(it)
                view.setBackgroundResource(R.drawable.passcode_fill_circle)
            }
        }
    }


    // fun getText() => if (type === 'dots') dots else editText.getText()

}

private fun performDefaultPassCode() {


    fun performPassCode() {

    }

}




