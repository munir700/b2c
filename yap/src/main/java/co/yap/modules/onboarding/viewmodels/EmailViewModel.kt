package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Build
import co.yap.app.login.EncryptionUtils
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.states.EmailState
import co.yap.networking.authentication.AuthRepository
import co.yap.networking.authentication.requestdtos.DemographicDataRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.SendVerificationEmailRequest
import co.yap.networking.onboarding.requestdtos.SignUpRequest
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager

class EmailViewModel(application: Application) : OnboardingChildViewModel<IEmail.State>(application), IEmail.ViewModel,
    IRepositoryHolder<ObnoardingRepository> {

    override val state: EmailState = EmailState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: ObnoardingRepository = ObnoardingRepository
    private val authRepository: AuthRepository = AuthRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)


    override fun onResume() {
        super.onResume()
        setProgress(80)
    }

    override fun handlePressOnNext() {
        signUp()
    }


    private fun signUp() {
        launch {
            state.loading = true
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
                is RetroApiResponse.Success -> {
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_PASSCODE,
                        EncryptionUtils.encrypt(context, parentViewModel!!.onboardingData.passcode)!!
                    )
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_USERNAME,
                        EncryptionUtils.encrypt(context, state.twoWayTextWatcher)!!
                    )
                    sendVerificationEmail()
                    postDemographicData()
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    private fun sendVerificationEmail() {
        launch {
            state.loading = true
            when (val response = repository.sendVerificationEmail(
                SendVerificationEmailRequest(
                    state.twoWayTextWatcher,
                    parentViewModel!!.onboardingData.accountType.toString()
                )
            )) {
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    private fun postDemographicData() {
        val deviceId: String? = sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
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
                is RetroApiResponse.Success -> getAccountInfo()
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

    private fun getAccountInfo() {
        launch {
            state.loading = true
            when (val response = repository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    parentViewModel!!.onboardingData.ibanNumber = response.data.data[0].iban
                    nextButtonPressEvent.value = true
                }
                is RetroApiResponse.Error -> state.toast = response.error.message
            }
            state.loading = false
        }
    }

}