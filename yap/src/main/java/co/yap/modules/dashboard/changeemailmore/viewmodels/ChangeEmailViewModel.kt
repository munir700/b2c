package co.yap.modules.dashboard.changeemailmore.viewmodels

import android.app.Application
import co.yap.modules.dashboard.changeemailmore.interfaces.IChangeEmail
import co.yap.modules.dashboard.changeemailmore.states.ChangeEmailState
import co.yap.yapcore.BaseViewModel


class ChangeEmailViewModel(application: Application) :
    BaseViewModel<IChangeEmail.State>(application), IChangeEmail.ViewModel {
    override val state: ChangeEmailState = ChangeEmailState()
}