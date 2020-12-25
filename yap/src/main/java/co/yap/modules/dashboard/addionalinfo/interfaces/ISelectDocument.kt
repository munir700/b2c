package co.yap.modules.dashboard.addionalinfo.interfaces

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.addionalinfo.adapters.UploadAdditionalDocumentAdapter
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.yapcore.IBase
import java.io.File

interface ISelectDocument {
    interface View : IBase.View<ViewModel>

    interface ViewModel : IBase.ViewModel<State> {
        val uploadAdditionalDocumentAdapter: UploadAdditionalDocumentAdapter
        fun moveToNext()
        fun uploadDocument(file: File, documentType: String, success: () -> Unit)
        fun setEnabled(list: List<AdditionalDocument>, isUploaded: (Boolean) -> Unit)
        fun setSubTitle(isUploaded: Boolean)
    }

    interface State : IBase.State {
        val valid: ObservableBoolean
        val title: ObservableField<String>
        val subTitle: ObservableField<String>
    }
}