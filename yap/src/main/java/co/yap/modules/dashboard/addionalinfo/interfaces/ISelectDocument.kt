package co.yap.modules.dashboard.addionalinfo.interfaces

import co.yap.modules.dashboard.addionalinfo.adapters.UploadAdditionalDocumentAdapter
import co.yap.yapcore.IBase
import java.io.File

interface ISelectDocument {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val uploadAdditionalDocumentAdapter: UploadAdditionalDocumentAdapter
        fun moveToNext()
        fun uploadDocument(file: File, id: String, success: () -> Unit)
    }

    interface State : IBase.State
}