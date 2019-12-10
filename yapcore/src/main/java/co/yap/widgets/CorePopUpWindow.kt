package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Build
import android.transition.Fade
import android.transition.TransitionManager
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.layout_pop_up_window.view.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("CustomViewStyleable")
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class CorePopUpWindow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyle, defStyleRes) {



    private var viewWeight: Int = 0
    private var viewHeight: Int = 0
     var countryCode: String = "+971 "
    lateinit var typedArray: TypedArray
    var inputType: Int = 0

    var maxLength: Int = 0

    private var viewDataBinding: ViewDataBinding

    init {
        viewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_pop_up_window, this, true)
        viewDataBinding.executePendingBindings()
//        editText = findViewWithTag("input")

        attrs?.let {
            typedArray = context.obtainStyledAttributes(it, R.styleable.CorePopUpWindow, 0, 0)
            val title = resources.getText(
                typedArray
                    .getResourceId(R.styleable.CorePopUpWindow_textValue, R.string.empty_string)
            )

            ivPopUp.setOnClickListener {
                // Initialize a new layout inflater instance
//                val inflater:LayoutInflater = LayoutInflater.from(context)
                 val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

                // Inflate a custom view using layout inflater
                val view = inflater.inflate(R.layout.pop_up_view, null)

                // Initialize a new instance of popup window
                val popupWindow = PopupWindow(
                    view, // Custom view to show in popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
                    LinearLayout.LayoutParams.WRAP_CONTENT // Window height
                )

                // Set an elevation for the popup window
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    popupWindow.elevation = 10.0F
                }


                // If API level 23 or higher then execute the code
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Create a new slide animation for popup window enter transition
                    val slideIn = Fade()
//                slideIn.slideEdge = Gravity.TOP
                    popupWindow.enterTransition = slideIn

                    // Slide animation for popup window exit transition
                    val slideOut = Fade()
//                slideOut.slideEdge = Gravity.RIGHT
                    popupWindow.exitTransition = slideOut

                }

                // Get the widgets reference from custom view
                val tv = view.findViewById<TextView>(R.id.text_view)
                val buttonPopup = view.findViewById<Button>(R.id.button_popup)

                // Set click listener for popup window's text view
                tv.setOnClickListener {
                    // Change the text color of popup window's text view
                    tv.setTextColor(Color.RED)
                }

                // Set a click listener for popup's button widget
                buttonPopup.setOnClickListener {
                    // Dismiss the popup window
                    popupWindow.dismiss()
                }

                // Set a dismiss listener for popup window
                popupWindow.setOnDismissListener {
                    Toast.makeText(context, "Popup closed", Toast.LENGTH_SHORT).show()
                }


                // Finally, show the popup window on app
                TransitionManager.beginDelayedTransition(root_layout)
                popupWindow.showAtLocation(
                    root_layout, // Location to display popup window
                    Gravity.CENTER, // Exact position of layout to display popup
                    0, // X offset
                    0 // Y offset
                )
            }


            typedArray.recycle()


        }

    }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        viewWeight = w
        viewHeight = h
    }

}
