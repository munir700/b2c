package co.yap.yapcore

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.view.KeyEvent
import android.graphics.drawable.Drawable
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
import co.yap.widgets.CoreButton
import co.yap.widgets.CoreInputField
import co.yap.yapcore.interfaces.IBindable

object UIBinder {
    @BindingAdapter("bitmap")
    @JvmStatic
    fun setImageBitmap(view: ImageView, bitmap: Bitmap?) {
        if (bitmap != null)
            view.setImageBitmap(bitmap)
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageResId(view: ImageView, resId: Int) {
        view.setImageResource(resId)
    }

    @BindingAdapter("src")
    @JvmStatic
    fun setImageResId(view: ImageView, drawable: Drawable?) {
        if (drawable != null)
            view.setImageDrawable(drawable)
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

    /* core input text field */


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("enableCoreButton")
    fun setEnable(view: CoreButton, enable: Boolean) {
        if (null != enable) {
            view.enableButton(enable)
        }
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputText")
    fun setText(view: CoreInputField, textValue: String) {
        view.setview_input_text(textValue)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputDrawableLeft")
    fun drawableLeft(view: CoreInputField, drawable: Drawable) {
        view.setDrawableLeftIcon(drawable)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputDrawableRight")
    fun drawableRight(view: CoreInputField, drawable: Drawable?) {
        view.setDrawableRightIcon(drawable)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("coreInputError")
    fun setErrorMessage(view: CoreInputField, error: String) {
        if (null != error && !error.isEmpty()) {
            view.settingUIForError(error)
        } else {
            view.settingUIForNormal()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("resetUI")
    fun resetUI(view: CoreInputField, refresh: Boolean) {
        if (refresh) {
            view.settingUIForNormal()

        }
    }

    /* textwatcher */

    @JvmStatic
    @BindingAdapter("textWatcher")
    fun setTextChangeListener(view: CoreInputField, watcher: TextWatcher) {
        view.editText.addTextChangedListener(watcher)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("cursorPlacement")
    fun cursorPlacement(view: CoreInputField, placeCursor: Boolean) {
        view.cursorPlacement()

    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("inputText")
    fun setInputText(view: CoreInputField, text: SpannableStringBuilder) {
        view.editText.setText(text)
        view.editText.setSelection(view.editText.text.length)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("isCursorVisible")
    fun isCursorVisible(view: CoreInputField, isVisible: Boolean) {
        view.editText.setCursorVisible(isVisible)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("selection")
    fun selection(view: CoreInputField, selection: Int) {
        view.editText.setSelection(selection)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @JvmStatic
    @BindingAdapter("disableKeyBack")
    fun disableKeyBack(view: CoreInputField, index: Int) {

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


    //    @BindingAdapter("progress")
//    @JvmStatic
//    fun setProgress(progressBar: ProgressBar, progress: Int) {
    // will update the "progress" propriety of seekbar until it reaches progress
//        ObjectAnimator animation = ObjectAnimator.ofInt(seekbar, "progress", progress);
//        animation.setDuration(500); // 0.5 second
//        animation.setInterpolator(new DecelerateInterpolator());
//        animation.start();
//    }


}