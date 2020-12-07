package co.yap.modules.imagepreviewer

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IImagePreViewer {
    interface State : IBase.State {
        var imageUrl: ObservableField<String>? //       https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg
        var imageReceiptTitle: ObservableField<String>?
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
        fun deleteReceipt()
    }

    interface View : IBase.View<ViewModel>

}