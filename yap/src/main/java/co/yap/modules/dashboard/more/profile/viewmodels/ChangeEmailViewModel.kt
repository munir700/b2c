package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.modules.dashboard.more.profile.intefaces.IChangeEmail
import co.yap.modules.dashboard.more.profile.states.ChangeEmailState
import co.yap.modules.dashboard.more.viewmodels.MoreBaseViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpGenericRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.constants.Constants


class ChangeEmailViewModel(application: Application) :
    MoreBaseViewModel<IChangeEmail.State>(application), IChangeEmail.ViewModel,
    IRepositoryHolder<CustomersRepository> {
    override val changeEmailSuccessEvent: SingleClickEvent= SingleClickEvent()

    override val repository: CustomersRepository = CustomersRepository
    override val clickEvent: SingleClickEvent = SingleClickEvent()
    override val success: MutableLiveData<Boolean> = MutableLiveData()
    private val messagesRepository: MessagesRepository = MessagesRepository

    override val state: ChangeEmailState =
        ChangeEmailState(application)


    override fun onHandlePressOnNextButton() {
        if (state.confirmEmailValidation()) {

            launch {
                state.loading = true
                when (val response =
                    repository.validateEmail(state.newEmail)) {
                    is RetroApiResponse.Success -> {
                       createOtp()
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.errorMessage = response.error.message

                    }

                }
            }
        }
    }


    private fun createOtp() {
        launch {
            when (val response =
                messagesRepository.createOtpGeneric(createOtpGenericRequest = CreateOtpGenericRequest(
                    Constants.CHANGE_EMAIL))) {
                is RetroApiResponse.Success -> {
                    success.value = true
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                    success.value = false
                }
            }
            state.loading = false
        }
    }

    override fun changeEmail() {
        launch {
            state.loading = true
            when (val response =
                repository.changeVerifiedEmail(state.newEmail)) {
                is RetroApiResponse.Success -> {
                    changeEmailSuccessEvent.call()
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }
            }
            state.loading = false
        }
    }


    override fun onResume() {
        super.onResume()
        setToolBarTitle("Change email")
    }
}