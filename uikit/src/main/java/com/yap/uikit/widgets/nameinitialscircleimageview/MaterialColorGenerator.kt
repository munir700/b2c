package com.yap.uikit.widgets.nameinitialscircleimageview

import android.graphics.Color
import androidx.annotation.ColorInt
import java.util.*

class MaterialColorGenerator : ColorGenerator {

    @ColorInt
    private val defaultBackgroundColors = Arrays.asList(
        "#29A682FF", //Primary soft
        "#29F57F17", //Orange
        "#29F44774", //Magenta
        "#29478DF4", //Secondary blue
        "#2900B9AE", //green
        "#295E35B1"  //light primary
    )

    @ColorInt
    private val defaultTextColors = Arrays.asList(
        "#A682FF", //Primary soft
        "#F57F17", //Orange
        "#F44774", //Magenta
        "#478DF4", //Secondary blue
        "#00b9ae", //green
        "#5E35B1"  //light primary
    )

    @ColorInt
    private val mColors: List<Int>

    @ColorInt
    private val mTextColors: List<Int>

    constructor() {
        this.mColors = defaultBackgroundColors.map { str -> Color.parseColor(str) }
        this.mTextColors = defaultTextColors.map { str -> Color.parseColor(str) }
    }

    constructor(@ColorInt bgColors: List<Int>, @ColorInt textColors: List<Int>) {
        this.mColors = bgColors
        this.mTextColors = textColors
    }

    override fun generateBackgroundColor(index: Int): Int {
        return mColors[index % mColors.size]
    }

    override fun generateTextColor(index: Int): Int {
        return mTextColors[index % mTextColors.size]
    }
}