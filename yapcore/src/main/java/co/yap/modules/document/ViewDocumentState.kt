package co.yap.modules.document

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

open class ViewDocumentState : BaseState(), IViewDocumentActivity.State {

    override var fileType: MutableLiveData<String>? = MutableLiveData()
    override var imageUrlForImageView: MutableLiveData<String>? = MutableLiveData()
    override val isPDF: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isNeedToShowOnlyUpdateOption: MutableLiveData<Boolean> = MutableLiveData(false)
    override var filePath: MutableLiveData<String>? = MutableLiveData()
    override val isFileUpdated: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isEditable: MutableLiveData<Boolean> = MutableLiveData(false)
}