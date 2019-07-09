package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.app.constants.Constants
import co.yap.app.login.EncryptionUtils
import co.yap.modules.onboarding.interfaces.IPhoneVerificationSignIn
import co.yap.modules.onboarding.states.PhoneVerificationSignInState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.authentication.requestdtos.VerifyOtpRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.BaseViewModel
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class PhoneVerificationSignInViewModel(application: Application) :
    BaseViewModel<IPhoneVerificationSignIn.State>(application), IPhoneVerificationSignIn.ViewModel,
    IRepositoryHolder<AuthRepository> {

    override val repository: AuthRepository = AuthRepository

    override val state: PhoneVerificationSignInState = PhoneVerificationSignInState()
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val verifyOtpResult: SingleLiveEvent<Boolean> = SingleLiveEvent()


    override fun handlePressOnSendButton() {
        nextButtonPressEvent.postValue(true)
    }

    override fun handlePressOnResendOTP() {

    }

    override fun verifyOtp() {
        launch {
            when (val response =
                repository.verifyOtp(VerifyOtpRequest(Constants.ACTION_DEVICE_VERIFICATION, state.otp))) {
                is RetroApiResponse.Success -> {
                    val sharedPreferenceManager = SharedPreferenceManager(context)
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_PASSCODE,
                        EncryptionUtils.encrypt(context, state.passcode)!!
                    )
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_USERNAME,
                        EncryptionUtils.encrypt(context, state.username)!!
                    )
                    verifyOtpResult.postValue(true)
                }
                is RetroApiResponse.Error -> {
                    state.toast = response.error.message
                }
            }

        }
    }
}