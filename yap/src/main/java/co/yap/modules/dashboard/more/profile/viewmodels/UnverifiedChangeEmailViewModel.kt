package co.yap.modules.dashboard.more.profile.viewmodels

import android.app.Application
import androidx.lifecycle.MutableLiveData

class UnverifiedChangeEmailViewModel(application: Application) :ChangeEmailViewModel(application) {
    override val success: MutableLiveData<Boolean> = MutableLiveData()
    override fun onHandlePressOnNextButton() {
        if (state.confirmEmailValidation()) {
            success.value = true
          /*  launch {
                state.loading = true
                when (val response =
                    repository.validateEmail(state.newEmail)) {
                    is RetroApiResponse.Success -> {
                       // createOtp()
                    }

                    is RetroApiResponse.Error -> {
                        state.loading = false
                        state.errorMessage = response.error.message

                    }

                }
            }*/
        }
    }
}