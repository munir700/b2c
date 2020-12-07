package co.yap.modules.dashboard.transaction.previewreceipt

import android.net.Uri
import androidx.databinding.Bindable
import co.yap.BR
import co.yap.yapcore.BaseState

class PreviewTransactionReceiptState : BaseState(), IPreviewTransactionReceipt.State {
    @get:Bindable
    override var filePath: Uri? = Uri.EMPTY
        set(value) {
            field = value
            notifyPropertyChanged(BR.filePath)
        }
}
