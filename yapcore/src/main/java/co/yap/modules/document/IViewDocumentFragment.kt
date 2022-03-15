package co.yap.modules.document

import android.net.Uri
import androidx.lifecycle.MutableLiveData
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
        var pdfFile: File?
        var fileForUpdate: File?
        fun getUploadDocumentOptions(): java.util.ArrayList<BottomSheetItem>
        fun handlePressView(id: Int)
        fun downloadFile(filePath: String, success: (file: File?) -> Unit)
        fun getDialogueOptions(): ArrayList<BottomSheetItem>
    }

    interface State : IBase.State {
        var fileType: MutableLiveData<String>?
        val imageUrlForImageView: MutableLiveData<String>?
        val isPDF: MutableLiveData<Boolean>
        val isImage: MutableLiveData<Boolean>
        val isDeleteAble: MutableLiveData<Boolean>
        val isUpdateAble: MutableLiveData<Boolean>
        val isNeedToShowUpdateDialogue: MutableLiveData<Boolean>
        val filePath: MutableLiveData<String>?
        val isEditable: MutableLiveData<Boolean>
        val isNeedToRefreshView: MutableLiveData<Boolean>
    }
}