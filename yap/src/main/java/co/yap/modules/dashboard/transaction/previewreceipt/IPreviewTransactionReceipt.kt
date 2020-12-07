package co.yap.modules.dashboard.transaction.previewreceipt

import android.net.Uri
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

class IPreviewTransactionReceipt {
    interface View : IBase.View<ViewModel> {
        fun registerObserver()
        fun unRegisterObserver()
    }

    interface ViewModel : IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }

    interface State : IBase.State {
        var filePath: Uri?
    }
}
