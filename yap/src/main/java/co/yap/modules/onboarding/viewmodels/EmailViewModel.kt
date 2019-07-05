package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.states.EmailState
import co.yap.yapcore.BaseViewModel

class EmailViewModel(application: Application) : BaseViewModel<IEmail.State>(application), IEmail.ViewModel {

    override val state: EmailState = EmailState(application)


    override fun handlePressOnNext() {

        state.valid = false

    }
}