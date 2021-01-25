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
    override var receiptArray: ArrayList<String> = arrayListOf()
    override val state: ImagePreViewerState =
        ImagePreViewerState()

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }

    override fun deleteReceipt() {
        launch {
            state.loading = true
            when(val response = transactionsRepository.deleteTransactionReceipt(transactionId,receiptArray)){
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