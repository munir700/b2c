package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import co.yap.modules.dashboard.addionalinfo.adapters.UploadAdditionalDocumentAdapter
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.states.SelectDocumentState
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument

class SelectDocumentViewModel(application: Application) :
    AdditionalInfoBaseViewModel<ISelectDocument.State>(application),
    ISelectDocument.ViewModel {
    override val uploadAdditionalDocumentAdapter: UploadAdditionalDocumentAdapter =
        UploadAdditionalDocumentAdapter(context, mutableListOf())

    override fun moveToNext() {
        moveStep()
    }

    override val state: ISelectDocument.State = SelectDocumentState(application)
    override fun onCreate() {
        super.onCreate()
        showHeader(true)
        setTitle("Additional Information")
        setSubTitle("You have successfully uploaded your documents. Please click Next to continue")
        uploadAdditionalDocumentAdapter.setList(getDocumentList())
    }

    fun getMockableList(): ArrayList<AdditionalDocument> {
//        val list: ArrayList<AdditionalDocument> = arrayListOf()
//        list.add(AdditionalDocument(0, "Passport Copy", false))
//        list.add(AdditionalDocument(0, "Visa Copy", false))
//        list.add(AdditionalDocument(0, "Passport Copy", false))
        return getDocumentList() as ArrayList<AdditionalDocument>
    }
}