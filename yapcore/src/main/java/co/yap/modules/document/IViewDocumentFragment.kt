package co.yap.modules.document

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import java.io.File

interface IViewDocumentFragment {

    interface View : IBase.View<ViewModel> {
        fun removeObservers()
        fun addObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var fileForUpdate: File?
        fun getUploadDocumentOptions(): ArrayList<BottomSheetItem>
        fun handlePressView(id: Int)
        fun downloadFile(filePath: String, success: (file: File?) -> Unit)
        fun getDialogueOptions(): ArrayList<BottomSheetItem>
        fun getEmploymentInfoApiCall()
    }

    interface State : IBase.State {
        var fileDataFromRefreshApi: MutableLiveData<Document>
        var isPDF: MutableLiveData<Boolean>
        var isUpdateAble: MutableLiveData<Boolean>
        var isDeleteAble: MutableLiveData<Boolean>
        var isNeedToShowUpdateDialogue: MutableLiveData<Boolean>
        var isEditable: MutableLiveData<Boolean>
        var documentType: MutableLiveData<String>
        var fileExtension: MutableLiveData<String>
        var fileUrl: MutableLiveData<String>
    }
}