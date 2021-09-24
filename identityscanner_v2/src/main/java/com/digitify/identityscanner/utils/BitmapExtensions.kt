package com.digitify.identityscanner.utils

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

fun Bitmap.toBlackWhite(threshold: Int = 128): Bitmap? {
    val bitmap = copy(config, true)
    var A: Int;
    var R: Int;
    var G: Int;
    var B: Int;
    var pixel: Int

    for (x in 0 until width) {
        for (y in 0 until height) {
            // get pixel color
            pixel = getPixel(x, y)
            A = Color.alpha(pixel)
            R = Color.red(pixel)
            G = Color.green(pixel)
            B = Color.blue(pixel)
            var gray = (0.2989 * R + 0.5870 * G + 0.1140 * B).toInt()

            // use 128 as default threshold, above -> white, below -> black
            gray = if (gray > threshold) {
                255
            } else {
                0
            }

            // set new pixel color to output bitmap
            bitmap.setPixel(x, y, Color.argb(A, gray, gray, gray))
        }
    }
    return bitmap
}