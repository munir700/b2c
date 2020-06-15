package co.yap.modules.pdf

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class PDFViewModel(application: Application) :
    BaseViewModel<IPDFActivity.State>(application),
    IPDFActivity.ViewModel {
    override val state: PDFState = PDFState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun downloadFile(filePath: String, success: (file: File?) -> Unit) {
        launch {
            state.loading = true
            getLocalContacts(filePath)?.let {
                success.invoke(it)
            } ?: success.invoke(null)
            state.loading = false
        }
    }

    override fun handlePressView(id: Int) {
    }

    private suspend fun getLocalContacts(path: String) = viewModelBGScope.async(Dispatchers.IO) {
        downloadPDF(path)
    }.await()

    private fun downloadPDF(path: String): File? {

//        val url: URL
////        var urlConnection: HttpURLConnection? = null
////        try {
////            url = URL(path)
////            urlConnection = url
////                .openConnection() as HttpURLConnection
////            val `in`: InputStream = urlConnection.inputStream
////            val isw = InputStreamReader(`in`)
////            var data: Int = isw.read()
////            while (data != -1) {
////                val current = data.toChar()
////                data = isw.read()
////                print(current)
////            }
////        } catch (e: Exception) {
////            e.printStackTrace()
////        } finally {
////            urlConnection?.disconnect()
////        }
        try {
            val url = URL(path)
            val urlConnection = url.openConnection() as HttpURLConnection
            val file = File.createTempFile("121", ".pdf")
            val fileOutput = FileOutputStream(file)
            val inputStream = urlConnection.inputStream

            val buffer = ByteArray(1024)
            var bufferLength: Int = 0
            while (true) {
                bufferLength = inputStream.read(buffer)
                if (bufferLength < 0) {
                    break
                } else
                    fileOutput.write(buffer, 0, bufferLength)
            }
            fileOutput.close()
            return file
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
    }
}