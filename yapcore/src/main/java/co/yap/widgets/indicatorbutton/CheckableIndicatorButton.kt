/*
package co.yap.widgets.indicatorbutton

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.view.children
import co.yap.yapcore.R
import kotlinx.android.synthetic.main.indicator_button.view.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.children

open class CheckableIndicatorButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatRadioButton(context, attrs) {

    interface OnCheckedChangeListener {
        fun onCheckedChanged(view: View, isChecked: Boolean)
    }

    private val listeners = mutableListOf<OnCheckedChangeListener>()

    private var checked: Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.indicator_button, null)
        context.theme.obtainStyledAttributes(attrs, R.styleable.CheckableIndicatorButton, 0, 0).use {
            isEnabled = it.getBoolean(R.styleable.CheckableIndicatorButton_android_enabled, isEnabled)
            label.text = it.getText(R.styleable.CheckableIndicatorButton_android_text)
            labe2.text = it.getText(R.styleable.CheckableIndicatorButton_text2)
        }
        isClickable = true
        isFocusable = true
        background = context.getDrawable(R.drawable.checkable_background_selector)
    }

    fun addOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener) {
        listeners.add(onCheckedChangeListener)
    }

    fun removeOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener) {
        listeners.remove(onCheckedChangeListener)
    }


    override fun isChecked() = checked

    @InternalCoroutinesApi
    override fun toggle() {
        isChecked = !checked
    }

    @InternalCoroutinesApi
    override fun setChecked(checked: Boolean) {
        if (this.checked != checked) {
            this.checked = checked
            children.filter { it is Checkable }.forEach { (it as Checkable).isChecked = checked }
            listeners.forEach { it.onCheckedChanged(this, this.checked) }
            refreshDrawableState()
        }
    }

    @InternalCoroutinesApi
    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }


    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        }
        return drawableState
    }

    companion object {
        //Step 4: introduce checked state
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }
    */
/*override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        children.forEach { it.isEnabled = enabled }
    }*//*

}*/
