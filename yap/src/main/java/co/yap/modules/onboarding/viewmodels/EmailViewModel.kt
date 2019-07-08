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
        signUp()
    }


    private fun signUp() {
        launch {
            when (val response = repository.signUp(
                SignUpRequest(
                    parentViewModel!!.onboardingData.firstName,
                    parentViewModel!!.onboardingData.lastName,
                    parentViewModel!!.onboardingData.countryCode,
                    parentViewModel!!.onboardingData.mobileNo,
                    state.email,
                    "5550",
                    parentViewModel!!.onboardingData.accountType.toString()
                )
            )) {
                is RetroApiResponse.Success -> nextButtonPressEvent.postValue(true)
                is RetroApiResponse.Error -> state.error = response.error.message
            }
        }
    }

    private fun sendVerifiationEmail() {
        launch {
            when (val response = repository.sendVerificationEmail(SendVerificationEmailRequest("", ""))) {
                is RetroApiResponse.Success -> ""
                is RetroApiResponse.Error -> state.error = response.error.message
            }
        }
    }
}