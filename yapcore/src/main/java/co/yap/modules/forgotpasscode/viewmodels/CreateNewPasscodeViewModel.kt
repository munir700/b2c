package co.yap.modules.forgotpasscode.viewmodels

import android.app.Application
import co.yap.app.login.EncryptionUtils
import co.yap.networking.admin.AdminRepository
import co.yap.networking.admin.requestdtos.ForgotPasscodeRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.helpers.SharedPreferenceManager

class CreateNewPasscodeViewModel(application: Application) : CreatePasscodeViewModel(application),
    IRepositoryHolder<AdminRepository> {
    override val repository: AdminRepository = AdminRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)

    override fun handlePressOnCreatePasscodeButton(id: Int) {
        if (validateAggressively()) {
            forgotPasscode(id)
        }
    }

    private fun forgotPasscode(id: Int) {
        launch {
            state.loading = true
            when (val response = repository.forgotPasscode(ForgotPasscodeRequest(mobileNumber, state.passcode))) {
                is RetroApiResponse.Success ->{
                    nextButtonPressEvent.setValue(id)
                    state.loading = false
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_PASSCODE,
                        EncryptionUtils.encrypt(context, state.passcode)!!
                    )
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                    state.loading = false
                }

            }
        }
    }
}