package com.yap.uikit.widgets.nameinitialscircleimageview

import androidx.annotation.ColorInt

interface ColorGenerator {
    @ColorInt
    fun generateBackgroundColor(index: Int): Int

    @ColorInt
    fun generateTextColor(index: Int): Int
}