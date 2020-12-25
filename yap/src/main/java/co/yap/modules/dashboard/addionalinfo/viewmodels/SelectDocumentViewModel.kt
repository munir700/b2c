package co.yap.modules.dashboard.addionalinfo.viewmodels

import android.app.Application
import android.os.Build
import co.yap.modules.dashboard.addionalinfo.adapters.UploadAdditionalDocumentAdapter
import co.yap.modules.dashboard.addionalinfo.interfaces.ISelectDocument
import co.yap.modules.dashboard.addionalinfo.states.SelectDocumentState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.models.additionalinfo.AdditionalDocument
import co.yap.networking.customers.requestdtos.UploadAdditionalInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.translation.Strings
import co.yap.yapcore.helpers.extentions.sizeInMb
import co.yap.yapcore.managers.SessionManager
import id.zelory.compressor.Compressor
import java.io.File

class SelectDocumentViewModel(application: Application) :
    AdditionalInfoBaseViewModel<ISelectDocument.State>(application),
    ISelectDocument.ViewModel, IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    override val uploadAdditionalDocumentAdapter: UploadAdditionalDocumentAdapter =
        UploadAdditionalDocumentAdapter(context, mutableListOf())

    override fun moveToNext() {
        moveStep()
    }

    override val state: ISelectDocument.State = SelectDocumentState(application)
    override fun onCreate() {
        super.onCreate()
        state.title.set(getString(Strings.screen_additional_info_label_text_additional_info))
        uploadAdditionalDocumentAdapter.setList(getDocumentList())
        setEnabled(uploadAdditionalDocumentAdapter.getDataList()) {
            setSubTitle(it)
        }
    }


    override fun uploadDocument(file: File, documentType: String, success: () -> Unit) {

        launch {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                Compressor.compress(context, file) {
                    upload(file, documentType) {
                        success()
                    }
                }
            } else {
                upload(file, documentType) {
                    success()
                }
            }
        }
    }

    override fun setEnabled(list: List<AdditionalDocument>, isUploaded: (Boolean) -> Unit) {
        val list = list.filter { additionalDocument -> additionalDocument.status == "PENDING" }
        state.valid.set(list.isEmpty())
        isUploaded.invoke(list.isEmpty())
    }

    private fun upload(file: File, documentType: String, success: () -> Unit) {
        launch {
            if (file.sizeInMb() < 25) {
                state.loading = true
                when (val response = repository.uploadAdditionalDocuments(
                    UploadAdditionalInfo(
                        files = file,
                        documentType = documentType
                    )
                )) {
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        file.deleteRecursively()
                        success()
                    }
                    is RetroApiResponse.Error -> {
                        showToast(response.error.message)
                        state.loading = false
                        file.deleteRecursively()
                    }
                }
            } else {
                showToast("File size not supported")
                file.deleteRecursively()
            }
        }

    }

    override fun setSubTitle(isUploaded: Boolean) {
        if (isUploaded) {
            state.subTitle.set(getString(Strings.screen_additional_info_label_text_upload_document_complete))
        } else {
            state.subTitle.set(
                SessionManager.user?.currentCustomer?.firstName + getString(
                    Strings.screen_additional_info_label_text_upload_document
                )
            )
        }
    }
}
