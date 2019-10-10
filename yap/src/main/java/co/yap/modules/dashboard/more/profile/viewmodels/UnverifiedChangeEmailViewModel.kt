package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IUnverifiedChangeEmail
import co.yap.modules.dashboard.more.profile.states.UnverifiedChangeEmailState
import co.yap.yapcore.BaseViewModel

class UnverifiedChangeEmailViewModel(application: Application):
    BaseViewModel<IUnverifiedChangeEmail.State>(application),IUnverifiedChangeEmail.ViewModel {
    override val state: UnverifiedChangeEmailState=UnverifiedChangeEmailState()
}