package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Build
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.states.EmailState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.authentication.requestdtos.DemographicDataRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.SendVerificationEmailRequest
import co.yap.networking.onboarding.requestdtos.SignUpRequest
import co.yap.networking.onboarding.responsedtos.SignUpResponse
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class EmailViewModel(application: Application) : OnboardingChildViewModel<IEmail.State>(application), IEmail.ViewModel,
    IRepositoryHolder<ObnoardingRepository> {

    override val state: EmailState = EmailState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: ObnoardingRepository = ObnoardingRepository
    val authRepository: AuthRepository = AuthRepository

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
                    state.twoWayTextWatcher,
                    parentViewModel!!.onboardingData.passcode,
                    parentViewModel!!.onboardingData.accountType.toString()
                )
            )) {
                is RetroApiResponse.Success -> { postDemographicData() }
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

    private fun postDemographicData() {

        val sharedPreferenceManager = SharedPreferenceManager(context)
        val deviceId: String? = sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            when (val response = authRepository.postDemographicData(
                DemographicDataRequest(
                    "LOGIN",
                    Build.VERSION.RELEASE,
                    deviceId.toString(),
                    Build.BRAND,
                    Build.MODEL,
                    "Android"
                )
            )) {
                is RetroApiResponse.Success -> nextButtonPressEvent.postValue(true)
                is RetroApiResponse.Error -> state.error = response.error.message
            }
        }
    }

}