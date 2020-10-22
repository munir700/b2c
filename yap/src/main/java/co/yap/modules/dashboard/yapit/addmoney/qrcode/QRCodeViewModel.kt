package co.yap.modules.dashboard.yapit.addmoney.qrcode

import android.app.Application
import android.view.View
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.managers.SessionManager

class QRCodeViewModel (application: Application) :
        BaseViewModel<IQRCode.State>(application),
        IQRCode.ViewModel {
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val state: QRCodeState = QRCodeState()

    override fun handlePressOnView(id: Int) {
    }

    override fun onCreate() {
        super.onCreate()
        populateState()
    }

    private fun populateState() {
        SessionManager.user?.let {
            state.fullName = it.currentCustomer.getFullName()
            if (it.currentCustomer.getPicture() != null) {
                state.profilePictureUrl = it.currentCustomer.getPicture()!!
                state.nameInitialsVisibility = View.GONE
            } else {
                state.fullName = it.currentCustomer.getFullName()
                state.nameInitialsVisibility = View.VISIBLE
            }

        }
    }

}