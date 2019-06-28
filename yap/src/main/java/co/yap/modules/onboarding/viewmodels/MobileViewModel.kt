package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.yapcore.BaseViewModel

class MobileViewModel(application: Application) : BaseViewModel<IMobile.State>(application), IMobile.ViewModel {
    override val state: IMobile.State
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun validateMobileNumber() {

    }

    override fun handlePressOnNext() {

    }
}