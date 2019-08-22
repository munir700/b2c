package co.yap.widgets.graph.data

import android.graphics.Rect

data class Frame(val left: Float, val top: Float, val right: Float, val bottom: Float)

fun Frame.toRect(): Rect =
    Rect(this.left.toInt(), this.top.toInt(), this.right.toInt(), this.bottom.toInt())