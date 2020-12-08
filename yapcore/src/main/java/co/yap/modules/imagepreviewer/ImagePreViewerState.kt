package co.yap.modules.imagepreviewer

import android.net.Uri
import androidx.databinding.ObservableField
import co.yap.yapcore.BaseState

class ImagePreViewerState : BaseState(), IImagePreViewer.State {

    override var imageUri: ObservableField<Uri>? = ObservableField()

    override var imageUrl: ObservableField<String>? = ObservableField()

    override var imageReceiptTitle: ObservableField<String>? = ObservableField()

}