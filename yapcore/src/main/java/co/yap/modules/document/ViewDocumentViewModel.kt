package co.yap.modules.document

import android.app.Application
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.transactions.TransactionsRepository
import co.yap.translation.Strings
import co.yap.widgets.State
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.R
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.PhotoSelectionType
import co.yap.yapcore.helpers.extentions.createTempFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.ArrayList


class IViewDocumentViewModel(application: Application) :
    BaseViewModel<IViewDocumentFragment.State>(application),
    IViewDocumentFragment.ViewModel, IRepositoryHolder<TransactionsRepository> {
    override val state: ViewDocumentState = ViewDocumentState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var pdfFile: File? = null
    override val repository: TransactionsRepository = TransactionsRepository
    override var fileForUpdate: File? = null
    override fun downloadFile(filePath: String, success: (file: File?) -> Unit) {
        launch {
            state.stateLiveData?.postValue(State.loading(""))
            getPDFFileFromWeb(filePath)?.let {
                pdfFile = it
                success.invoke(it)
            } ?: success.invoke(null)
        }
    }

    override fun handlePressView(id: Int) {
        clickEvent.setValue(id)
    }

    private suspend fun getPDFFileFromWeb(path: String) = viewModelBGScope.async(Dispatchers.IO) {
        downloadPDF(path)
    }.await()

    private fun downloadPDF(path: String): File? {
        try {
            val url = URL(path)
            val urlConnection = url.openConnection() as HttpURLConnection
            val file = context.createTempFile("pdf")
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
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    override fun getDialogueOptions(): ArrayList<BottomSheetItem> {
        val list = arrayListOf<BottomSheetItem>()
        list.add(
            BottomSheetItem(
                title = getString(Strings.screen_update_profile_photo_display_text_open_camera),
                tag = PhotoSelectionType.CAMERA.name
            )
        )
        list.add(
            BottomSheetItem(
                title = getString(Strings.screen_transaction_details_display_sheet_text_upload_from_files),
                tag = PhotoSelectionType.GALLERY.name
            )
        )
        return list
    }

    override fun getUploadDocumentOptions(): ArrayList<BottomSheetItem> {
        val list = arrayListOf<BottomSheetItem>()
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_camera,
                title = getString(Strings.screen_update_profile_photo_display_text_open_camera),
                subTitle = getString(Strings.screen_upload_documents_display_sheet_text_scan_single_document),
                tag = PhotoSelectionType.CAMERA.name
            )
        )
        list.add(
            BottomSheetItem(
                icon = R.drawable.ic_file_manager,
                title = getString(Strings.screen_upload_documents_display_sheet_text_upload_from_files),
                subTitle = getString(Strings.screen_upload_documents_display_sheet_text_upload_from_files_descriptions),
                tag = PhotoSelectionType.GALLERY.name
            )
        )

        return list
    }
}