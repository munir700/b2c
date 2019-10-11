package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData
import co.yap.R
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.models.RetroApiResponse

class UnverifiedChangeEmailViewModel(application: Application) :ChangeEmailViewModel(application) {
    override val success: MutableLiveData<Boolean> = MutableLiveData()
    override val repository: CustomersRepository = CustomersRepository

    override fun onHandlePressOnNextButton() {
        if (state.confirmEmailValidation()) {

            launch {
                state.loading = true
                when (val response =
                    repository.validateEmail(state.newEmail)) {
                    is RetroApiResponse.Error -> {
                        changeUnverifiedEmailRequest()
                    }

                    is RetroApiResponse.Success -> {
                        state.loading = false
                        // state.errorMessage = response.error.message
                        state.drawableNew =  context.getDrawable(R.drawable.bg_edit_text_red_under_line)
                        state.drawableConfirm = context.getDrawable(R.drawable.bg_edit_text_red_under_line)

                    }

                }
            }
           // success.value = true
        }
    }
    private fun changeUnverifiedEmailRequest(){
        launch {
            when (val response =
                repository.changeUnverifiedEmail(state.newEmail)) {
                is RetroApiResponse.Success -> {
                    success.value = true
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.errorMessage = response.error.message

                }

            }
            state.loading = false
        }
    }
}