package co.yap.modules.dashboard.yapit.addmoney.qrcode

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.yapcore.BaseState

class QRCodeState : BaseState(), IQRCode.State {
    override var userNameImage: ObservableField<String>? = ObservableField("")

    @get:Bindable
    override var fullName: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }

    @get:Bindable
    override var nameInitialsVisibility: Int? = View.VISIBLE
        set(value) {
            field = value
            notifyPropertyChanged(androidx.databinding.library.baseAdapters.BR.nameInitialsVisibility)

        }

    @get:Bindable
    override var imageUri: Uri? = Uri.EMPTY
        set(value) {
            field = value
            notifyPropertyChanged(androidx.databinding.library.baseAdapters.BR.imageUri)
        }

    @get:Bindable
    override var profilePictureUrl: String? = ""
        set(value) {
            field = value
            notifyPropertyChanged(androidx.databinding.library.baseAdapters.BR.profilePictureUrl)
            if (!field.isNullOrEmpty()) {
                nameInitialsVisibility = View.VISIBLE
                notifyPropertyChanged(androidx.databinding.library.baseAdapters.BR.nameInitialsVisibility)

            } else {
                nameInitialsVisibility = View.GONE
                notifyPropertyChanged(androidx.databinding.library.baseAdapters.BR.nameInitialsVisibility)

            }
        }

    @get:Bindable
    override var qrBitmap: Bitmap? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.qrBitmap)
        }

}