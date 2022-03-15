package co.yap.modules.document

import androidx.lifecycle.MutableLiveData
import co.yap.yapcore.BaseState

open class ViewDocumentState : BaseState(), IViewDocumentFragment.State {

    override var fileType: MutableLiveData<String>? = MutableLiveData()
    override var imageUrlForImageView: MutableLiveData<String>? = MutableLiveData()
    override val isPDF: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isDeleteAble: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isUpdateAble: MutableLiveData<Boolean> = MutableLiveData(false)
    override var filePath: MutableLiveData<String>? = MutableLiveData()
    override val isImage: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isEditable: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isNeedToShowUpdateDialogue: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isNeedToRefreshView: MutableLiveData<Boolean> = MutableLiveData(false)
}