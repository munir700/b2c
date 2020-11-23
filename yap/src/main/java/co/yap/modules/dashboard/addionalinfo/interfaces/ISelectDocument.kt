package co.yap.modules.dashboard.addionalinfo.interfaces

import co.yap.modules.dashboard.addionalinfo.adapters.UploadAdditionalDocumentAdapter
import co.yap.yapcore.IBase

interface ISelectDocument {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val uploadAdditionalDocumentAdapter: UploadAdditionalDocumentAdapter
        fun moveToNext()
    }

    interface State : IBase.State
}