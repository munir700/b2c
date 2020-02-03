package co.yap.household.onboard.onboarding.viewmodels

import android.app.Application
import android.graphics.Color
import android.os.Build
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import co.yap.household.R
import co.yap.household.onboard.onboarding.interfaces.IEmail
import co.yap.household.onboard.onboarding.states.EmailState
import co.yap.household.onboard.onboarding.main.viewmodels.OnboardingChildViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.customers.requestdtos.SignUpRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import java.util.*
import java.util.concurrent.TimeUnit

class DialerViewModel(application: Application) :
    OnboardingChildViewModel<IEmail.State>(application), IEmail.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override var hasDoneAnimation: Boolean = false
    override var onEmailVerifySuccess: MutableLiveData<Boolean> = MutableLiveData(false)
    override val state: EmailState = EmailState(application)
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override val animationStartEvent: MutableLiveData<Boolean> = MutableLiveData()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override val repository: CustomersRepository = CustomersRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)

    override fun onResume() {
        super.onResume()
        updateBackground(Color.WHITE)
        setProgress(20)
    }

    override fun onCreate() {
        super.onCreate()
        state.emailTitle = getString(R.string.screen_new_user_email_display_text_title)
        state.emailBtnTitle = getString(R.string.screen_new_user_email_display_button_confirm)
        state.deactivateField = true
        state.emailVerificationTitle =
            getString(R.string.screen_new_user_email_display_text_email_caption)

    }

    override fun stopTimer() {
        parentViewModel?.onboardingData?.elapsedOnboardingTime =
            TimeUnit.MILLISECONDS.toSeconds(
                Date().time - (parentViewModel?.onboardingData?.startTime?.time ?: Date().time)
            )
    }


    private fun signUp() {
        launch {
            state.refreshField = true
            when (val response = repository.signUp(
                SignUpRequest(
                    parentViewModel?.onboardingData?.firstName,
                    parentViewModel?.onboardingData?.lastName,
                    parentViewModel?.onboardingData?.countryCode,
                    parentViewModel?.onboardingData?.mobileNo,
                    state.twoWayTextWatcher,
                    parentViewModel?.onboardingData?.passcode,
                    parentViewModel?.onboardingData?.accountType.toString()
                )
            )) {
                is RetroApiResponse.Success -> {
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                        true
                    )
                    parentViewModel?.onboardingData?.passcode?.let { passcode ->
                        sharedPreferenceManager.savePassCodeWithEncryption(passcode)
                    } ?: toast(context, "Invalid pass code")

                    //sharedPreferenceManager. saveUserNameWithEncryption(state.twoWayTextWatcher)

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
//        state.emailVerificationTitle = verificationText
        state.emailVerificationTitle =
            getString(R.string.screen_new_user_email_display_text_email_caption)


        // mark that we have completed all verification stuff to handle proper back navigation
        state.verificationCompleted = true
        setProgress(33)
        animationStartEvent.value = true
    }

    override fun sendVerificationEmail() {
        launch {
            state.loading = true
            state.refreshField = true
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

    override fun postDemographicData() {

        val deviceId: String? =
            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
        launch {
            state.valid = false
            state.loading = true
            state.refreshField = true
            when (val response = repository.postDemographicData(
                DemographicDataRequest(
                    "SIGNUP",
                    Build.VERSION.RELEASE,
                    deviceId.toString(),
                    Build.BRAND,
                    if (Utils.isEmulator()) "generic" else Build.MODEL,
                    "Android"
                )
            )) {
                is RetroApiResponse.Success -> getAccountInfo()
                is RetroApiResponse.Error -> {
                    state.valid = true
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
            state.refreshField = true
            when (val response = repository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNotEmpty()) {
//                        parentViewModel!!.onboardingData.ibanNumber = response.data.data[0].iban
//                        Handler().postDelayed({
//                            nextButtonPressEvent.setValue(EVENT_NAVIGATE_NEXT)
//                        }, 400)
//                        MyUserManager.user = response.data.data[0]
//                        MyUserManager.user?.setLiveData() // DOnt remove this line
                        state.valid = true
                    }
                }
                is RetroApiResponse.Error -> {
                    state.valid = true
                    state.toast = response.error.message
                }

            }
            state.loading = false
        }
    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (state.valid) {
                }
            }
            false
        }
    }
}