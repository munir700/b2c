package co.yap.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.ForgotPasscodeRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.R
import co.yap.yapcore.helpers.SharedPreferenceManager

class CreateNewPasscodeViewModel(application: Application) : CreatePasscodeViewModel(application),
    IRepositoryHolder<CustomersRepository> {
    override val repository: CustomersRepository = CustomersRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)
    var token: String = ""

    override fun handlePressOnCreatePasscodeButton(id: Int) {
        if (id == R.id.tvTermsAndConditions) {
            nextButtonPressEvent.setValue(id)
        } else {
            if (validateAggressively()) {
                forgotPasscode(id)
            }
        }
    }

    private fun forgotPasscode(id: Int) {
        launch {
            state.loading = true
            when (val response =
                repository.forgotPasscode(
                    ForgotPasscodeRequest(
                        mobileNumber,
                        state.passcode,
                        token
                    )
                )) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.setValue(id)
                    state.loading = false
                    sharedPreferenceManager.savePassCodeWithEncryption(state.passcode)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }

            }
        }
    }
}