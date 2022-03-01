package com.yap.yappakistan.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.view.View
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun Context.shareImage(
    rootView: View,
    imageName: String,
    shareText: String? = null,
    chooserTitle: String
) {
    val bitmap: Bitmap = takeScreenshotForView(rootView)
    var bmpUri: Uri? = null
    val fileName = "${imageName}.jpg"
    val file = createTempFile(fileName)
    try {
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    bmpUri = FileProvider.getUriForFile(
        this,
        "com.yap.pk.qa" + ".provider", file
    );

    val shareIntent = Intent()
    shareIntent.action = Intent.ACTION_SEND
    if (!shareText.isNullOrBlank()) {
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            shareText
        )
    }
    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri)
    shareIntent.type = "image/jpeg"
    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    if (shareIntent.resolveActivity(packageManager) != null)
        startActivity(Intent.createChooser(shareIntent, chooserTitle))
}

fun takeScreenshotForView(view: View): Bitmap {
    view.measure(
        View.MeasureSpec.makeMeasureSpec(view.width, View.MeasureSpec.EXACTLY),
        View.MeasureSpec.makeMeasureSpec(view.height, View.MeasureSpec.EXACTLY)
    )
    view.layout(
        view.x.toInt(),
        view.y.toInt(),
        view.x.toInt() + view.measuredWidth,
        view.y.toInt() + view.measuredHeight
    )
    val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    view.draw(canvas)
    return bitmap
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