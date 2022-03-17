package co.yap.modules.document

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.yapcore.BaseState

open class ViewDocumentState : BaseState(), IViewDocumentFragment.State {

    override var fileDataFromRefreshApi: MutableLiveData<Document> = MutableLiveData()
    override val isPDF: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isUpdateAble: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isDeleteAble: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isEditable: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isNeedToShowUpdateDialogue: MutableLiveData<Boolean> = MutableLiveData(false)
    override var documentType: MutableLiveData<String> = MutableLiveData()
    override var fileExtension: MutableLiveData<String> = MutableLiveData()
    override var fileUrl: MutableLiveData<String> = MutableLiveData()


}