package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.states.ChangeEmailState
import co.yap.yapcore.BaseViewModel


class ChangeEmailViewModel(application: Application) :
    BaseViewModel<IChangeEmail.State>(application), IChangeEmail.ViewModel {
    override val state: ChangeEmailState =
        ChangeEmailState()
}