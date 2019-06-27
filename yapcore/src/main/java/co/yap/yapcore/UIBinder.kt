package co.yap.yapcore

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.yap.translation.Translator
import co.yap.widgets.CoreInputField
import co.yap.yapcore.interfaces.IBindable

object UIBinder {
    @BindingAdapter("bitmap")
    @JvmStatic
    fun setImageBitmap(view: ImageView, bitmap: Bitmap) {
        view.setImageBitmap(bitmap)
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageResId(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

    @BindingAdapter("text")
    @JvmStatic
    fun setText(view: TextView, text: String) {
        view.text = Translator.getString(view.context, text)
    }

    @BindingAdapter("text")
    @JvmStatic
    fun setText(view: TextView, textId: Int) {
        view.text = Translator.getString(view.context, textId)
    }

    @BindingAdapter("hint")
    @JvmStatic
    fun setHint(view: TextView, textId: String) {
        view.hint = Translator.getString(view.context, textId)
    }

    @BindingAdapter("hint")
    @JvmStatic
    fun setHint(view: EditText, textId: String) {
        view.hint = Translator.getString(view.context, textId)
    }

    @BindingAdapter("selected")
    @JvmStatic
    fun setSelected(view: Button, selected: Boolean) {
        view.isSelected = selected
        view.isPressed = selected
    }

    @BindingAdapter("entries", "layout")
    @JvmStatic
    fun <T : IBindable> setEntries(
        viewGroup: ViewGroup,
        entries: List<T>?, layoutId: Int
    ) {
        viewGroup.removeAllViews()
        if (entries != null) {
            val inflater = viewGroup.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            for (i in entries.indices) {
                val entry = entries[i]
                val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, viewGroup, true)
                binding.setVariable(entry.bindingVariable, entry)
                binding.executePendingBindings()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputText")
    fun setText(view: CoreInputField, textValue: String) {
        view.setview_input_text(textValue)
    }


}