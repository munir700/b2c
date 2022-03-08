package co.yap.modules.dashboard.more.changepasscode.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.changepasscode.interfaces.IChangePassCode
import co.yap.modules.dashboard.more.changepasscode.states.ChangePassCodeState
import co.yap.modules.otp.getOtpMessageFromComposer
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.enums.OTPActions
import co.yap.yapcore.managers.SessionManager

class ChangePassCodeViewModel(application: Application) :
    BaseViewModel<IChangePassCode.State>(application = application),
    IChangePassCode.ViewModel {
    override val state: ChangePassCodeState =
        ChangePassCodeState()
    override val clickEvent: SingleClickEvent = SingleClickEvent()

    override fun onCreate() {
        super.onCreate()
        state.toolbarVisibility = false
        state.leftIcon.set(true)
        state.rightIcon.set(true)
    }

    override fun handlePressOnView(id: Int) {
        clickEvent.postValue(id)
    }
   override fun getOtpMessage(): String = context.getOtpMessageFromComposer(
        OTPActions.FORGOT_PASS_CODE.name,
        SessionManager.user?.currentCustomer?.firstName,
        "%s1",
        "%s2",
        SessionManager.helpPhoneNumber
    )

}
