package co.yap.sendmoney.scanqrcode

import android.app.Application
import co.yap.yapcore.BaseViewModel

class ScanQRCodeViewModel(application: Application) :
    BaseViewModel<IScanQRCode.State>(application),
    IScanQRCode.ViewModel {
    override val state: IScanQRCode.State = ScanQRCodeState()
}