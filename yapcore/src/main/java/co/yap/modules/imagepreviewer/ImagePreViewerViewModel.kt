package co.yap.modules.imagepreviewer

import android.app.Application
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class ImagePreViewerViewModel(application: Application) :
    BaseViewModel<IImagePreViewer.State>(application), IImagePreViewer.ViewModel {

    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override var transactionId: String = ""
    override var receiptId: String = ""
    override val state: ImagePreViewerState =
        ImagePreViewerState()

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }

    override fun deleteReceipt(success: () -> Unit) {
        launch {
            state.loading = true
            when (val response =
                transactionsRepository.deleteTransactionReceipt(transactionId, receiptId)) {
                is RetroApiResponse.Success -> {
                    response.data.let { resp ->
                        success()
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