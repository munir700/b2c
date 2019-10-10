package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IUnverifiedChangeEmailSuccess
import co.yap.modules.dashboard.more.profile.states.UnverifiedChangeEmailSuccessState
import co.yap.yapcore.BaseViewModel

class UnverifiedChangeEmailSuccessViewModel(application: Application):BaseViewModel<IUnverifiedChangeEmailSuccess.State>(application),IUnverifiedChangeEmailSuccess.ViewModel{
    override val state: UnverifiedChangeEmailSuccessState= UnverifiedChangeEmailSuccessState()

}