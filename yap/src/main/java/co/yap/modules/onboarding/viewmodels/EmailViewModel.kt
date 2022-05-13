package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.os.Build
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import co.yap.R
import co.yap.modules.onboarding.enums.OnboardingPhase
import co.yap.modules.onboarding.interfaces.IEmail
import co.yap.modules.onboarding.models.CountryCode
import co.yap.modules.onboarding.states.EmailState
import co.yap.networking.customers.CustomersRepository
import co.yap.networking.customers.requestdtos.DemographicDataRequest
import co.yap.networking.customers.requestdtos.SaveReferalRequest
import co.yap.networking.customers.requestdtos.SendVerificationEmailRequest
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.notification.NotificationsApi
import co.yap.networking.notification.NotificationsRepository
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.Constants.KEY_APP_UUID
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.toast
import co.yap.yapcore.leanplum.SignupEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.leanplum.trackEventWithAttributes
import co.yap.yapcore.managers.SessionManager
import co.yap.yapcore.managers.saveUserDetails
import co.yap.yapcore.managers.setCrashlyticsUser
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.TimeUnit

class EmailViewModel(application: Application) :
    OnboardingChildViewModel<IEmail.State>(application), IEmail.ViewModel,
    IRepositoryHolder<CustomersRepository> {

    override val state: EmailState = EmailState(application)
    override val nextButtonPressEvent: SingleClickEvent = SingleClickEvent()
    override val animationStartEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override val repository: CustomersRepository = CustomersRepository
    val notificationRepository: NotificationsApi = NotificationsRepository


    override fun onResume() {
        super.onResume()
        setProgress(70)
    }

    override fun onCreate() {
        super.onCreate()
        state.emailTitle = getString(R.string.screen_enter_email_b2c_display_text_title)
        state.emailBtnTitle = getString(R.string.common_button_next)
        state.deactivateField = true
    }

    override fun handlePressOnNext() {
        when {
            state.emailTitle == getString(R.string.screen_email_verification_display_text_title) -> nextButtonPressEvent.setValue(OnboardingPhase.EVENT_POST_DEMOGRAPHIC.id)
            parentViewModel?.onboardingData?.email.isNullOrBlank() -> nextButtonPressEvent.postValue(OnboardingPhase.EVENT_POST_VERIFICATION_EMAIL.id)
            else -> nextButtonPressEvent.setValue(OnboardingPhase.NOTIFICATION_KFS_FLOW.id)
        }
    }

    override fun stopTimer() {
        parentViewModel?.onboardingData?.elapsedOnboardingTime =
            TimeUnit.MILLISECONDS.toSeconds(
                Date().time - (parentViewModel?.onboardingData?.startTime?.time ?: Date().time)
            )
    }

    fun setVerificationLabel() {
        state.emailTitle = getString(R.string.screen_email_verification_display_text_title)
        state.emailBtnTitle = getString(R.string.common_button_next)
        state.deactivateField = false
        val verificationText =
            "${parentViewModel?.onboardingData?.firstName} , ${getString(R.string.screen_email_verification_b2c_display_text_email_sent)}  ${state.twoWayTextWatcher} \n \n  ${
                getString(R.string.screen_email_verification_b2c_display_text_email_confirmation)
            }"
        state.emailVerificationTitle = verificationText
        // mark that we have completed all verification stuff to handle proper back navigation
        state.verificationCompleted = true
        setProgress(100)
        animationStartEvent.value = true
        state.setSuccessUI()
        requestSaveReferral()
    }

    override fun sendVerificationEmail() {
        launch {
            state.loading = true
            state.refreshField = true
            parentViewModel?.state?.emailError = false
            when (val response = repository.sendVerificationEmail(
                SendVerificationEmailRequest(
                    state.twoWayTextWatcher,
                    parentViewModel?.onboardingData?.accountType.toString(),
                    parentViewModel?.onboardingData?.token.toString()
                )
            )) {
                is RetroApiResponse.Error -> {
                    state.emailError = response.error.message
                    parentViewModel?.state?.emailError = true
                    state.loading = false

                }
                is RetroApiResponse.Success -> {
                    parentViewModel?.onboardingData?.token = response.data.token
                    parentViewModel?.onboardingData?.email = state.twoWayTextWatcher
                    state.loading = false
                    nextButtonPressEvent.postValue(OnboardingPhase.NOTIFICATION_KFS_FLOW.id)
                }
            }
        }
    }

    override fun postDemographicData() {
        val deviceId: String? =
            SharedPreferenceManager.getInstance(context).getValueString(KEY_APP_UUID)
        launch {
            state.valid = false
            state.loading = true
            state.refreshField = true
            parentViewModel?.state?.emailError = false
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
                is RetroApiResponse.Success -> {
                    getAccountInfo()
                }
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
            parentViewModel?.state?.emailError = false
            when (val response = repository.getAccountInfo()) {
                is RetroApiResponse.Success -> {
                    if (response.data.data.isNotEmpty()) {
                        SessionManager.getSystemConfigurationInfo(context)
                        val accountInfo: AccountInfo = response.data.data[0]
                        parentViewModel?.onboardingData?.ibanNumber = accountInfo.iban
                        delay(500)
                        SessionManager.user = accountInfo
                        SessionManager.user.setCrashlyticsUser()
                        context.saveUserDetails(
                            SessionManager.user?.currentCustomer?.mobileNo,
                            CountryCode.UAE.countryCode,
                            SharedPreferenceManager.getInstance(context).getValueBoolien(
                                Constants.KEY_IS_REMEMBER, true
                            )
                        )
                        SessionManager.setupDataSetForBlockedFeatures(SessionManager.card.value)
                        trackEventWithAttributes(SessionManager.user)
                        state.valid = true
                        state.isWaiting = accountInfo.isWaiting
                        state.loading = false
                        nextButtonPressEvent.setValue(OnboardingPhase.EVENT_NAVIGATE_NEXT.id)
                    }
                }
                is RetroApiResponse.Error -> {
                    state.valid = true
                    state.toast = response.error.message
                    state.loading = false

                }

            }
        }
    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (state.valid) {
                    nextButtonPressEvent.postValue(OnboardingPhase.EVENT_POST_VERIFICATION_EMAIL.id)
                    //  handlePressOnNext()
                }
            }
            false
        }
    }

    private fun requestSaveReferral() {
        SharedPreferenceManager.getInstance(context).getReferralInfo()?.let {
            launch {
                when (val response =
                    repository.saveReferalInvitation(SaveReferalRequest(it.id, it.date))) {

                    is RetroApiResponse.Success -> {
                        SharedPreferenceManager.getInstance(context).setReferralInfo(null)
                    }
                    is RetroApiResponse.Error -> {
                    }
                }
            }
        }
    }

}