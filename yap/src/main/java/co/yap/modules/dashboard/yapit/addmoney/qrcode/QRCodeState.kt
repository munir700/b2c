package co.yap.modules.dashboard.yapit.addmoney.qrcode

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import co.yap.BR
import co.yap.yapcore.BaseState

class QRCodeState : BaseState(), IQRCode.State {
    override var userNameImage: ObservableField<String> = ObservableField("")

    @get:Bindable
    override var fullName: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.fullName)
        }
}