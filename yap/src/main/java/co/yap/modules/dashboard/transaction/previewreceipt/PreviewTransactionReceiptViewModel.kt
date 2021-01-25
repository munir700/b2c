package co.yap.modules.dashboard.transaction.previewreceipt

import android.app.Application
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.sizeInMb
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class PreviewTransactionReceiptViewModel(application: Application) :
    BaseViewModel<IPreviewTransactionReceipt.State>(application),
    IPreviewTransactionReceipt.ViewModel {
    override val state = PreviewTransactionReceiptState()
    override val clickEvent = SingleClickEvent()
    override var transactionId: String= ""
    val repository: TransactionsRepository = TransactionsRepository

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }

    override fun saveTransactionReceipt(file : File) {
        launch {
            if (file.sizeInMb() < 25) {
                state.loading = true
                val reqFile =
                    RequestBody.create(MediaType.parse("image/${file.extension}"), file)
                val multiPartImageFile: MultipartBody.Part =
                    MultipartBody.Part.createFormData("receipt-images", file.name, reqFile)
                when(val response = repository.addTransactionReceipt(transactionId,multiPartImageFile)){
                    is RetroApiResponse.Success ->{
                        response.data.let { resp ->
                        }
                        state.loading = false

                    }
                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.toast = response.error.message
                    }
                }
            }
        }

    }
}
