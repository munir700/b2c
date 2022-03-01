package com.yap.uikit.widget.dialerpad

import android.view.View
import com.yap.uikit.widgets.dialerpad.KeyEvent

interface KeyClickListener {
    fun onKeyClicked(view: View,which: KeyEvent)
}