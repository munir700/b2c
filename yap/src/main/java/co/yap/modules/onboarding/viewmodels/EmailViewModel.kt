package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Build
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.R
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
    val authRepository: AuthRepository = AuthRepository

    override fun onResume() {
        super.onResume()
        setProgress(80)
    }

    override fun onCreate() {
        super.onCreate()
        state.emailTitle = getString(R.string.screen_enter_email_b2c_display_text_title)

    }

    override fun handlePressOnNext() {
        signUp()
        postDemographicData()
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
                    "5550",
                    parentViewModel!!.onboardingData.accountType.toString()
                )
            )) {
                is RetroApiResponse.Success -> {
                    state.emailTitle = getString(R.string.screen_email_verification_display_text_title)
                    setVerifictionLabel()
//                    nextButtonPressEvent.postValue(true)
                }
                is RetroApiResponse.Error -> state.error = response.error.message
            }
        }
    }

    private fun setVerifictionLabel() {

        val screen_email_verification_b2c_display_text_email_sent: String =
            getString(R.string.screen_email_verification_b2c_display_text_email_sent)
        val screen_email_verification_b2c_display_text_email_confirmation: String =
            getString(R.string.screen_email_verification_b2c_display_text_email_confirmation)
        val screen_email_verification_b2b_display_text_email_sent: String =
            getString(R.string.screen_email_verification_b2b_display_text_email_sent)
        val screen_email_verification_b2b_display_text_email_confirmation: String =
            getString(R.string.screen_email_verification_b2b_display_text_email_confirmation)

        val verificationText: String =
            parentViewModel!!.onboardingData.firstName + ", " + screen_email_verification_b2c_display_text_email_sent + "\n" + state.twoWayTextWatcher + "\n" + screen_email_verification_b2c_display_text_email_confirmation
        state.emailVerificationTitle = verificationText


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
        val sharedPreferenceManager: SharedPreferenceManager = SharedPreferenceManager(context)
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
                is RetroApiResponse.Success -> ""
                is RetroApiResponse.Error -> state.error = response.error.message
            }
        }
    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handlePressOnNext()
                }
                return false
            }
        }
    }
}