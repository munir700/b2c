package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.states.ChangeEmailState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent


class ChangeEmailViewModel(application: Application) :
    MoreBaseViewModel<IChangeEmail.State>(application), IChangeEmail.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val repository: CustomersRepository = CustomersRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val success: MutableLiveData<Boolean> = MutableLiveData()

    override val state: ChangeEmailState =
        ChangeEmailState(application)


    override fun onHandlePressOnNextButton() {
        if (state.confirmEmailValidation()) {

            launch {
                state.loading = true
                when (val response =
                    repository.validateEmail(state.newEmail)) {
                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.errorMessage = response.error.message
                        success.value = false

                    }
                    is RetroApiResponse.Success -> {
                        state.loading = false
                        success.value = true
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        setToolBarTitle("Change email")
    }
}