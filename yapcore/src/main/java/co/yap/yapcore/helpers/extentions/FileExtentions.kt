package co.yap.yapcore.helpers.extentions

import java.io.File

fun File.sizeInMb(): Int {
    return if (!exists()) 0 else {
        val size = length().toDouble()
        val sizeInKb = size / 1024
        val sizeInMb = sizeInKb / 1024
        return sizeInMb.toInt()
    }
}