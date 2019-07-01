package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.states.MobileState
import co.yap.yapcore.BaseViewModel

class MobileViewModel(application: Application) : BaseViewModel<IMobile.State>(application), IMobile.ViewModel {
    override fun validateMobileNumber(phoneNumber: String): Boolean? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override val state: IMobile.State
        get() = MobileState()


    override fun handlePressOnNext() {

    }
}