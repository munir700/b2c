package co.yap.widgets.radiocus

import android.view.View


interface RadioCheckable : Checkable {
    fun addOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener?)
    fun removeOnCheckChangeListener(onCheckedChangeListener: OnCheckedChangeListener?)
    interface OnCheckedChangeListener {
        fun onCheckedChanged(
            radioGroup: View?,
            isChecked: Boolean
        )
    }
}