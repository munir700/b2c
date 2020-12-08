package co.yap.modules.imagepreviewer

import android.app.Application
import co.yap.networking.transactions.TransactionsRepository
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class ImagePreViewerViewModel(application: Application) :
    BaseViewModel<IImagePreViewer.State>(application), IImagePreViewer.ViewModel {

    private val transactionsRepository: TransactionsRepository = TransactionsRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: ImagePreViewerState =
        ImagePreViewerState()

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }

    override fun deleteReceipt() {

    }
}