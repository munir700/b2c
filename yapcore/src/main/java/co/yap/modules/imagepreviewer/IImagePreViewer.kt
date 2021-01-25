package co.yap.modules.imagepreviewer

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IImagePreViewer {
    interface State : IBase.State {
        var imageUrl: ObservableField<String>?
        var imageReceiptTitle: ObservableField<String>?
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        var transactionId : String
        var receiptArray : ArrayList<String>
        fun handlePressOnView(id: Int)
        fun deleteReceipt()
    }

    interface View : IBase.View<ViewModel>

}