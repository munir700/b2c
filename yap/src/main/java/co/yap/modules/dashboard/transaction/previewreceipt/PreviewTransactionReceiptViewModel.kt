package co.yap.modules.dashboard.transaction.previewreceipt

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent

class PreviewTransactionReceiptViewModel(application: Application) :
    BaseViewModel<IPreviewTransactionReceipt.State>(application),
    IPreviewTransactionReceipt.ViewModel {
    override val state = PreviewTransactionReceiptState()
    override val clickEvent = SingleClickEvent()

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }
}
