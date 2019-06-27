package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.yapcore.BaseViewModel

class PhoneVerificationViewModel(application: Application) : BaseViewModel<IPhoneVerification.State>(application), IPhoneVerification.ViewModel {

    override val state: IPhoneVerification.State
        get() = PhoneVerificationState()
}