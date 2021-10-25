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

fun Bitmap.writeBitmapOnExternalStorage() {
    val root: String = Environment.getExternalStorageDirectory().toString()
    val myDir = File("$root/yap_scanner_images")
    myDir.mkdirs()

    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val fname = "Yap_$timeStamp.jpg"

    val file = File(myDir, fname)
    if (file.exists()) file.delete()
    try {
        val out = FileOutputStream(file)
        this.compress(Bitmap.CompressFormat.JPEG, 100, out)
        out.flush()
        out.close()
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
}
