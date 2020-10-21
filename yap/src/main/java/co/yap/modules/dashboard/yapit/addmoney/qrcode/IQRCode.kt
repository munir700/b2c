package co.yap.modules.dashboard.yapit.addmoney.qrcode

import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IQRCode {
    interface State : IBase.State {
        var fullName: String
        var userNameImage: ObservableField<String>
    }

    interface ViewModel: IBase.ViewModel<State> {
        val clickEvent: SingleClickEvent
        fun handlePressOnView(id: Int)
    }
    interface View : IBase.View<ViewModel> {

    }
}