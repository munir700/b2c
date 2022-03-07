package co.yap.modules.document

import androidx.lifecycle.MutableLiveData
import co.yap.widgets.bottomsheet.BottomSheetItem
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent
import java.io.File

interface IViewDocumentActivity {

    interface View : IBase.View<ViewModel> {
        fun removeObservers()
        fun addObservers()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var file: File?
        fun handlePressView(id: Int)
        fun downloadFile(filePath: String, success: (file: File?) -> Unit)
        fun getDialogueOptions(): ArrayList<BottomSheetItem>
    }

    interface State : IBase.State {
        var fileType: MutableLiveData<String>?
        val imageUrlForImageView: MutableLiveData<String>?
        val isPDF: MutableLiveData<Boolean>
        val isNeedToShowOnlyUpdateOption: MutableLiveData<Boolean>
        val filePath: MutableLiveData<String>?
        val isFileUpdated: MutableLiveData<Boolean>
    }
}