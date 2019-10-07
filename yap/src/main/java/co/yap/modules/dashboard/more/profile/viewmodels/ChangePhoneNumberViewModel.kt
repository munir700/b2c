package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IChangePhoneNumber
import co.yap.modules.dashboard.more.profile.states.ChangePhoneNumberState
import co.yap.yapcore.BaseViewModel

class ChangePhoneNumberViewModel(application: Application) :
    BaseViewModel<IChangePhoneNumber.State>(application), IChangePhoneNumber.ViewModel {
    override val state: ChangePhoneNumberState = ChangePhoneNumberState()
}