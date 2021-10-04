package com.digitify.identityscanner.utils

import android.graphics.Bitmap
import android.graphics.Color


class GlareDetector {
    private val glareThreshold = 0.1f
    private val whitePixelThreshold = 230

    suspend fun detectGlare(bitmap: Bitmap, callback: (found: Boolean) -> Unit) {
        val brightnessBitmap = bitmap.toBlackWhite(whitePixelThreshold)
        if (brightnessBitmap != null) {
            var whitePixels = 1
            var blackPixels = 0
            var R = 0
            var G = 0
            var B = 0
            val height: Int = brightnessBitmap.height
            val width: Int = brightnessBitmap.width
            val pixels = IntArray(width * height)
            brightnessBitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            var i = 0
            while (i < pixels.size) {
                val color = pixels[i]
                R = Color.red(color)
                G = Color.green(color)
                B = Color.blue(color)
                if (R >= 230 && G >= 230 && B >= 230) whitePixels++
                else blackPixels++
                i++
            }
            val percentage = (whitePixels.toDouble() / blackPixels) * 100
            callback.invoke(percentage >= glareThreshold)
        } else {
            callback.invoke(true)
        }
    }
}