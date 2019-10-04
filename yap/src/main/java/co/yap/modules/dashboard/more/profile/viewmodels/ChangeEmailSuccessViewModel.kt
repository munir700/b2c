package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmailSuccess
import co.yap.modules.dashboard.more.profile.states.ChangeEmailSuccessState
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.IBase

class ChangeEmailSuccessViewModel(application: Application) :
    BaseViewModel<IChangeEmailSuccess.State>(application), IChangeEmailSuccess.ViewModel {
    override val state: ChangeEmailSuccessState = ChangeEmailSuccessState()
}