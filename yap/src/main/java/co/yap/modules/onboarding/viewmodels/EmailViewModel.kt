package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Build
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.R
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
    override val animationStartEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: ObnoardingRepository = ObnoardingRepository
    private val authRepository: AuthRepository = AuthRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)


    override fun onResume() {
        super.onResume()
        setProgress(80)
    }

    override fun onCreate() {
        super.onCreate()
        state.emailTitle = getString(R.string.screen_enter_email_b2c_display_text_title)
        state.emailBtnTitle = getString(R.string.screen_phone_number_button_send)
        state.deactivateField = true

    }

    override fun handlePressOnNext() {
        if (state.emailTitle.equals(getString(R.string.screen_email_verification_display_text_title))) {
            postDemographicData()
        } else {
            sendVerificationEmail()

        }
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
                is RetroApiResponse.Success -> {
                    sharedPreferenceManager.save(SharedPreferenceManager.KEY_IS_USER_LOGGED_IN, true)
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_PASSCODE,
                        EncryptionUtils.encrypt(context, parentViewModel!!.onboardingData.passcode)!!
                    )
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_USERNAME,
                        EncryptionUtils.encrypt(context, state.twoWayTextWatcher)!!
                    )
                    state.loading = false
                    setVerificationLabel()
                }

                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.emailError = response.error.message
                }
            }
        }
    }

    private fun setVerificationLabel() {
        state.emailTitle = getString(R.string.screen_email_verification_display_text_title)
        state.emailBtnTitle = getString(R.string.common_button_next)
        state.deactivateField = false

        val screen_email_verification_b2c_display_text_email_sent: String =
            getString(R.string.screen_email_verification_b2c_display_text_email_sent)
        val screen_email_verification_b2c_display_text_email_confirmation: String =
            getString(R.string.screen_email_verification_b2c_display_text_email_confirmation)
        val screen_email_verification_b2b_display_text_email_sent: String =
            getString(R.string.screen_email_verification_b2b_display_text_email_sent)
        val screen_email_verification_b2b_display_text_email_confirmation: String =
            getString(R.string.screen_email_verification_b2b_display_text_email_confirmation)

        val verificationText: String =
            parentViewModel!!.onboardingData.firstName + ", " + screen_email_verification_b2c_display_text_email_sent + " " + state.twoWayTextWatcher + "\n" + "\n" + screen_email_verification_b2c_display_text_email_confirmation
        state.emailVerificationTitle = verificationText


        // mark that we have completed all verification stuff to handle proper back navigation
        state.verificationCompleted = true
        setProgress(100)
        animationStartEvent.value = true
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
                is RetroApiResponse.Error -> {
                    state.emailError = response.error.message
                    state.loading = false

                }
                is RetroApiResponse.Success -> {
                    signUp()
                }
            }
        }
    }

    private fun postDemographicData() {
        val deviceId: String? = sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.loading = true
            when (val response = authRepository.postDemographicData(
                DemographicDataRequest(
                    "SIGNUP",
                    Build.VERSION.RELEASE,
                    deviceId.toString(),
                    Build.BRAND,
                    Build.MODEL,
                    "Android"
                )
            )) {
                is RetroApiResponse.Success -> getAccountInfo()
                is RetroApiResponse.Error -> {
                    state.loading = false
                    state.toast = response.error.message
                }
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

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (state.valid) {
                        handlePressOnNext()
                    }
                }
                return false
            }
        }
    }
}