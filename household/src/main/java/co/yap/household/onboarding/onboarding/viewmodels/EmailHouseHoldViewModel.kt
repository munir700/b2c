package co.yap.household.onboarding.onboarding.viewmodels

import android.app.Application
import android.os.Build
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.app.login.EncryptionUtils
import co.yap.household.R
import co.yap.household.onboarding.onboarding.interfaces.IEmail
import co.yap.household.onboarding.onboarding.states.EmailState
import co.yap.household.onboarding.viewmodels.OnboardingChildViewModel
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.customers.requestdtos.SignUpRequest
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import java.util.*
import java.util.concurrent.TimeUnit

class EmailHouseHoldViewModel(application: Application) :
    OnboardingChildViewModel<IEmail.State>(application), IEmail.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val state: EmailState = EmailState(application)
    override var hasDoneAnimation: Boolean= false
    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val animationStartEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: CustomersRepository = CustomersRepository
    private val sharedPreferenceManager = SharedPreferenceManager(context)

    override fun onResume() {
        super.onResume()
        setProgress(80)

    }

    override fun onCreate() {
        super.onCreate()
        state.emailTitle = getString(R.string.screen_new_user_email_display_text_title)
        state.emailBtnTitle = getString(R.string.screen_new_user_email_display_button_confirm)
        state.deactivateField = true
        state.emailVerificationTitle =
            getString(R.string.screen_new_user_email_display_text_email_caption)

    }

    override fun handlePressOnNext() {
//        if (state.emailTitle == getString(R.string.screen_email_verification_display_text_title)) {
//            nextButtonPressEvent.setValue(EVENT_POST_DEMOGRAPHIC)
//        } else {
//        animationStartEvent.value = true

        nextButtonPressEvent.setValue(EVENT_POST_VERIFICATION_EMAIL)
//        }
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
                        SharedPreferenceManager.KEY_IS_USER_LOGGED_IN,
                        true
                    )
                    sharedPreferenceManager.save(
                        SharedPreferenceManager.KEY_PASSCODE,
                        EncryptionUtils.encrypt(
                            context,
                            parentViewModel!!.onboardingData.passcode
                        )!!
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
//        state.emailVerificationTitle = verificationText
        state.emailVerificationTitle =
            getString(R.string.screen_new_user_email_display_text_email_caption)


        // mark that we have completed all verification stuff to handle proper back navigation
        state.verificationCompleted = true
        setProgress(80)
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
        setProgress(100)
        animationStartEvent.value = true


//        val deviceId: String? =
//            sharedPreferenceManager.getValueString(SharedPreferenceManager.KEY_APP_UUID)
//        launch {
//            state.valid = false
//            state.loading = true
//            state.refreshField = true
//            when (val response = repository.postDemographicData(
//                DemographicDataRequest(
//                    "SIGNUP",
//                    Build.VERSION.RELEASE,
//                    deviceId.toString(),
//                    Build.BRAND,
//                    if (Utils.isEmulator()) "generic" else Build.MODEL,
//                    "Android"
//                )
//            )) {
//                is RetroApiResponse.Success -> getAccountInfo()
//                is RetroApiResponse.Error -> {
//                    state.valid = true
//                    state.loading = false
//                    state.toast = response.error.message
//                }
//            }
//            state.loading = false
//        }
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
                    handlePressOnNext()
                }
            }
            false
        }
    }
}