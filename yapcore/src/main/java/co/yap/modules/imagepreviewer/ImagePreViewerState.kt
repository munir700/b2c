package co.yap.modules.imagepreviewer

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class ImagePreViewerState : BaseState(), IImagePreViewer.State {

    override var imageUrl: ObservableField<String>? = ObservableField()

    override var imageReceiptTitle: ObservableField<String>? = ObservableField()

}