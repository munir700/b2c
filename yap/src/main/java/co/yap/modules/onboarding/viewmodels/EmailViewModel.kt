package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.states.EmailState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.SendVerificationEmailRequest
import co.yap.networking.onboarding.requestdtos.SignUpRequest
import co.yap.yapcore.SingleLiveEvent

class EmailViewModel(application: Application) : OnboardingChildViewModel<IEmail.State>(application), IEmail.ViewModel,
    IRepositoryHolder<ObnoardingRepository> {

    override val state: EmailState = EmailState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: ObnoardingRepository = ObnoardingRepository

    override fun onResume() {
        super.onResume()
        setProgress(80)
    }

    override fun handlePressOnNext() {
        nextButtonPressEvent.postValue(true)
    }


    private fun signUp() {
        launch {
            when (val response = repository.signUp(SignUpRequest("", "", "", "", "", "", ""))) {
                is RetroApiResponse.Success -> ""
                is RetroApiResponse.Error -> ""
            }
        }
    }

    private fun sendVerifiationEmail() {
        launch {
            when (val response = repository.sendVerificationEmail(SendVerificationEmailRequest("", ""))) {
                is RetroApiResponse.Success -> ""
                is RetroApiResponse.Error -> ""
            }
        }
    }
}