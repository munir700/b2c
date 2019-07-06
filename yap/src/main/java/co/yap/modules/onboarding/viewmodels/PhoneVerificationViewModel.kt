package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IPhoneVerification
import co.yap.modules.onboarding.states.PhoneVerificationState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.VerifyOtpRequest
import co.yap.yapcore.SingleLiveEvent

class PhoneVerificationViewModel(application: Application) :
    OnboardingChildViewModel<IPhoneVerification.State>(application), IPhoneVerification.ViewModel,
    IRepositoryHolder<ObnoardingRepository> {

    override val state: PhoneVerificationState = PhoneVerificationState()
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: ObnoardingRepository = ObnoardingRepository

    override fun onResume() {
        super.onResume()
        setProgress(40)
    }

    override fun handlePressOnSendButton() {
        nextButtonPressEvent.postValue(true)
    }

    override fun handlePressOnResendOTP() {

    }

    private fun verifyOtp() {
        launch {
            when (val response = repository.verifyOtp(VerifyOtpRequest("", "", ""))) {
                is RetroApiResponse.Success -> ""
                is RetroApiResponse.Error -> ""
            }
        }
    }
}