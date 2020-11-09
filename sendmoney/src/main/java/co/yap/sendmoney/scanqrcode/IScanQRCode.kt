package co.yap.sendmoney.scanqrcode

import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.databinding.ObservableField
import co.yap.yapcore.IBase
import co.yap.yapcore.SingleClickEvent

interface IScanQRCode {
    interface State : IBase.State {

    }

    interface ViewModel : IBase.ViewModel<State> {

    }

    interface View : IBase.View<ViewModel> {

    }
}