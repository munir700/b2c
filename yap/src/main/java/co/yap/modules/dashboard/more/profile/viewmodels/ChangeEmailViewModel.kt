package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.states.ChangeEmailState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel


class ChangeEmailViewModel(application: Application) :
    MoreBaseViewModel<IChangeEmail.State>(application), IChangeEmail.ViewModel {

    override val state: ChangeEmailState =
        ChangeEmailState(application)


    override fun onHandlePressOnNextButton() {
        if (state.checkEmailValidation()) {
            state.toast = "m validate"
        }
    }


    override fun onResume() {
        super.onResume()
        setToolBarTitle("Change email")
    }
}