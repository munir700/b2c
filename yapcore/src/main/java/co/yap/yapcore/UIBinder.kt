package co.yap.yapcore

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
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
import co.yap.widgets.ComponentCoreButton
import co.yap.widgets.ComponentCoreInputField
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
    @BindingAdapter("enableCoreButton")
    fun setEnable(view: ComponentCoreButton, enable: Boolean) {
        if (null != enable) {
            view.enableButton(enable)
        }
    }

    /* core input text field */


    @BindingAdapter("coreInputHint")
    @JvmStatic
    fun setHint(view: ComponentCoreInputField, hint: String) {
        view.editText.setHint(hint)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputText")
    fun setText(view: ComponentCoreInputField, textValue: String) {
        view.setview_input_text(textValue)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputDrawableLeft")
    fun drawableLeft(view: ComponentCoreInputField, drawable: Drawable) {
        view.setDrawableLeftIcon(drawable)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputDrawableRight")
    fun drawableRight(view: ComponentCoreInputField, drawable: Drawable?) {
        view.setDrawableRightIcon(drawable)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputError")
    fun setErrorMessage(view: ComponentCoreInputField, error: String) {
        if (null != error && !error.isEmpty()) {
            view.settingUIForError(error)
        } else {
            view.settingUIForNormal()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("resetUI")
    fun resetUI(view: ComponentCoreInputField, refresh: Boolean) {
        if (refresh) {
            view.settingUIForNormal()

        }
    }

    /* textwatcher */

    @JvmStatic
    @BindingAdapter("textWatcher")
    fun setTextChangeListener(view: ComponentCoreInputField, watcher: TextWatcher) {
        view.editText.addTextChangedListener(watcher)
    }

    @JvmStatic
    @BindingAdapter("changeCoreInputFocus")
    fun setFocusChangeListener(view: ComponentCoreInputField, focusChangeListener: View.OnFocusChangeListener) {
        view.editText.setOnFocusChangeListener(focusChangeListener)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("cursorPlacement")
    fun cursorPlacement(view: ComponentCoreInputField, placeCursor: Boolean) {
        view.cursorPlacement()

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("inputText")
    fun setInputText(view: ComponentCoreInputField, text: SpannableStringBuilder) {
        view.editText.setText(text)
        view.editText.setSelection(view.editText.text.length)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("isCursorVisible")
    fun isCursorVisible(view: ComponentCoreInputField, isVisible: Boolean) {
        view.editText.setCursorVisible(isVisible)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("selection")
    fun selection(view: ComponentCoreInputField, selection: Int) {
        view.editText.setSelection(selection)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("disableKeyBack")
    fun disableKeyBack(view: ComponentCoreInputField, index: Int) {

        if (view.editText.text.toString().length == 5) {
            view.editText.setCursorVisible(false)
            /* disable backpress */
            view.cursorPlacement()
            view.editText.setOnKeyListener(object : View.OnKeyListener {

                override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (index <= 5) {
                            return true
                        }
                    }
                    return false
                }
            })
        } else {
            /*enable backpress*/

            view.cursorPlacement()
            view.editText.setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (index <= 5) {
                            return true
                        }
                    }
                    return false
                }
            })
        }
    }

    /* end region textwatcher */

}