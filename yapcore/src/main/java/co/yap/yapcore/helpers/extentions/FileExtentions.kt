package co.yap.yapcore.helpers.extentions

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

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

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
@Throws(IOException::class)
fun Context.dummyEID(): File? {
    val file = this.createTempFile(".jpg")
    if (!file.exists()) {
        val asset: InputStream = this.assets.open("eid_doc.jpg")
        val output = FileOutputStream(file)
        val buffer = ByteArray(1024)
        var size: Int
        while (asset.read(buffer).also { size = it } != -1) {
            output.write(buffer, 0, size)
        }
        asset.close()
        output.close()
        return file
    }
    return null
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

fun Context?.getJsonDataFromAsset(fileName: String): String? {
    var jsonString: String? = null
    this?.let {
        try {
            jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
    }
    return jsonString
}
