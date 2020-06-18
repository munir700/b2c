package co.yap.yapcore.helpers.extentions

import android.content.Context
import java.io.File

fun File.sizeInMb(): Int {
    return if (!exists()) 0 else {
        val size = length().toDouble()
        val sizeInKb = size / 1024
        val sizeInMb = sizeInKb / 1024
        return sizeInMb.toInt()
    }
}

fun Context.createTempFile(extension: String): File {
    val dir = File(this.filesDir, "yapTemp")
    if (!dir.exists()) {
        dir.mkdirs()
        dir.mkdir()
    }
    val time = System.currentTimeMillis().toString()
    return File(dir, "${time}.$extension")
}

fun Context.getTempFolder(): File {
    return File(this.filesDir, "yapTemp")
}

fun Context.deleteTempFolder(): Boolean {
    val file = File(this.filesDir, "yapTemp")
    return file.deleteRecursively()
}

fun File.deleteRecursivelyYap(): Boolean {
    return deleteRecursively()
}