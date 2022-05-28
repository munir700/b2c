package co.yap.modules.document

import androidx.lifecycle.MutableLiveData
import co.yap.networking.customers.responsedtos.employment_amendment.Document
import co.yap.yapcore.BaseState

open class ViewDocumentState : BaseState(), IViewDocumentFragment.State {

    override var fileDataFromRefreshApi: MutableLiveData<Document> = MutableLiveData()
    override var isPDF: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isUpdateAble: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isDeleteAble: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isEditable: MutableLiveData<Boolean> = MutableLiveData(false)
    override var isNeedToShowUpdateDialogue: MutableLiveData<Boolean> = MutableLiveData(false)
    override var documentType: MutableLiveData<String> = MutableLiveData()
    override var fileExtension: MutableLiveData<String> = MutableLiveData()
    override var fileUrl: MutableLiveData<String> = MutableLiveData()
}