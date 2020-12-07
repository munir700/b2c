package co.yap.modules.imagepreviewer

import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class ImagePreViewerState : BaseState(), IImagePreViewer.State {

    override var imageUrl: ObservableField<String>? =
        ObservableField() // https://scoopak.com/wp-content/uploads/2013/06/free-hd-natural-wallpapers-download-for-pc.jpg

    override var imageReceiptTitle: ObservableField<String>? = ObservableField()

}