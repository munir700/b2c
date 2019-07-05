package co.yap.yapcore.binders

import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import co.yap.widgets.ComponentCoreInputField


object CoreInputUiBinder {

    @BindingAdapter("coreInputHint")
    @JvmStatic
    fun setHint(view: ComponentCoreInputField, hint: String) {
        view.editText.setHint(hint)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputText")
    fun setText(view: ComponentCoreInputField, textValue: String) {
        view.editText.setText(textValue)
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
    fun setSpannableInputText(view: ComponentCoreInputField, text: SpannableStringBuilder) {
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
        val lengthh: Int = view.editText.text.toString().length
        if (view.editText.text.toString().length == 5) {
            view.editText.setCursorVisible(false)
            /* disable backpress */
            view.cursorPlacement()
            view.editText.setOnKeyListener(object : View.OnKeyListener {

                override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_DEL) {
                        if (lengthh <= 5) {
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
                        if (lengthh <= 5) {
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
