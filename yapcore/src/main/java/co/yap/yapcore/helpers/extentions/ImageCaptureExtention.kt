package co.yap.yapcore.helpers.extentions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.MediaScannerConnection
import android.os.Environment
import android.view.View
import co.yap.yapcore.helpers.DateUtils
import java.io.File
import java.io.FileOutputStream
import java.util.*


fun storeBitmap(rootView: View, context: Context) {
    val bitmap: Bitmap = takeScreenshotForView(rootView)
    val image_date = ""+getCurrentDateTime()
    val image_name = "YAP-"+image_date+"-qrCode"
    val root = Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_PICTURES
    ).toString()
    val myDir = File("$root/yap_qr_codes")
    myDir.mkdirs()
    val fname = image_name + ".jpg"
    val file = File(myDir, fname)
    if (file.exists()) file.delete()
    try {
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    MediaScannerConnection.scanFile(context, arrayOf(file.toString()), null,
        MediaScannerConnection.OnScanCompletedListener { path, uri ->
        })
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

fun getCurrentDateTime(): String {
    var currentCalendar: Calendar = Calendar.getInstance()
    val date = DateUtils.dateToString(currentCalendar.time, "dd-mm-yyyy")
    return date

}