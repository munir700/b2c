package co.yap.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.databinding.Bindable
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import co.yap.yapcore.BR
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
) : RelativeLayout(context, attrs, defStyle, defStyleRes) ,Observable{


    var drawableRight: Drawable? = null
    var drawableLeft: Drawable? = null
    private var paintText: Paint = Paint()

    private var viewWeight: Int = 0
    private var viewHeight: Int = 0
    private var textInput: String = ""
    lateinit var typedArray:TypedArray
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (propertyId == BR.view_input_text) {
//                    viewModel.state.email
                textInput =
                    typedArray.getString(R.styleable.CoreInputField_view_input_text)
                etEmail.setText(textInput)

            }
        }
    }
    init {

        LayoutInflater.from(context).inflate(R.layout.custom_widget_edit_text, this, true)
//        val layout: ConstraintLayout = LayoutInflater.from(context).inflate(R.layout.custom_widget_edit_text, this, true) as ConstraintLayout


        attrs?.let {
              typedArray = context.obtainStyledAttributes(it, R.styleable.CoreInputField, 0, 0)
            val title = resources.getText(
                typedArray
                    .getResourceId(R.styleable.CoreInputField_view_hint_input_field, R.string.empty_string)
            )
            val error = resources.getText(
                typedArray.getResourceId(R.styleable.CoreInputField_view_error_input_field, R.string.empty_string)
            )

            if (null != typedArray.getString(R.styleable.CoreInputField_view_input_text)) {

                textInput =
                typedArray.getString(R.styleable.CoreInputField_view_input_text)
            }
//            val textInput = resources.getText(
//                typedArray
//                    .getResourceId(R.styleable.CoreInputField_view_input_text, R.string.empty_string)
//            )

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

            if(!textInput.isEmpty()){
                etEmail.setText(textInput)

            }
            etEmail.hint = title
            if (error.isNotEmpty()) settingUIForError(error = error.toString()) else settingUIForNormal()
            typedArray.recycle()


            /* text paint styling */

            paintText.textAlign = Paint.Align.CENTER
            paintText.style = Paint.Style.FILL

        }

    }

    fun setview_input_text(text: String) {
        etEmail.setText(text)

    }

    @Bindable
    fun getview_input_text(): String? {
      return  etEmail.text.toString()

    }

    fun setText(text: String) {
        etEmail.setText(text)

    }


    @Bindable
    fun getText():String{
        return etEmail.text.toString()
    }

    fun viewSetTextTwo() {
        etEmail.setText(textInput)

    }

    fun settingUIForError(error: String) {
        etEmail.setBackgroundResource(R.drawable.bg_round_error_layout)
        tvError.text = error
        tvError.visibility = View.VISIBLE
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
