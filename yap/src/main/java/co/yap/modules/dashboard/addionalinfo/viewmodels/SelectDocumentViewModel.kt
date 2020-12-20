package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.adapters.UploadAdditionalDocumentAdapter
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.states.SelectDocumentState
import kotlinx.coroutines.delay
import java.io.File

class SelectDocumentViewModel(application: Application) :
    AdditionalInfoBaseViewModel<ISelectDocument.State>(application),
    ISelectDocument.ViewModel {
    override val uploadAdditionalDocumentAdapter: UploadAdditionalDocumentAdapter =
        UploadAdditionalDocumentAdapter(context, mutableListOf())

    override fun moveToNext() {
        moveStep()
    }

    override val state: ISelectDocument.State = SelectDocumentState(application)
    override fun onCreate() {
        super.onCreate()
        showHeader(true)
        setTitle("Additional Information")
        setSubTitle("You have successfully uploaded your documents. Please click Next to continue")
        uploadAdditionalDocumentAdapter.setList(getDocumentList())
    }

    override fun uploadDocument(file: File, id: String, success: () -> Unit) {
        launch {
            state.loading = true
            delay(3000)
            state.loading = false
            success()
        }
    }
}