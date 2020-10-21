package co.yap.modules.dashboard.yapit.addmoney.qrcode

import android.app.Application
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.extentions.maskAccountNumber
import co.yap.yapcore.helpers.extentions.maskIbanNumber
import co.yap.yapcore.managers.SessionManager

class QRCodeViewModel (application: Application) :
        BaseViewModel<IQRCode.State>(application),
        IQRCode.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: IQRCode.State = QRCodeState()

    override fun handlePressOnView(id: Int) {
    }

    override fun onResume() {
        super.onResume()
        populateState()
    }

    private fun populateState() {
        SessionManager.user?.let { it ->
            state.fullName = it.currentCustomer.getFullName()
            state.userNameImage.set(it.currentCustomer.getPicture() ?: "")
        }
    }
}