package com.yap.core.binders

import android.view.View
import androidx.databinding.BindingAdapter
import com.yap.core.extensions.showKeyboard

object CommonBinding {

    @JvmStatic
    @BindingAdapter(value = ["requestKeyboard", "forceKeyboard"], requireAll = false)
    fun setRequestKeyboard(view: View, request: Boolean, forced: Boolean) {
        view.showKeyboard(request, forced)
    }

    @BindingAdapter("enabled")
    @JvmStatic
    fun setEnabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
    }
}