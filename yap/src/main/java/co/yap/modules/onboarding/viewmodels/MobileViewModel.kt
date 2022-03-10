package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.models.LoadConfig
import co.yap.modules.onboarding.models.UserVerifierProvider
import co.yap.modules.onboarding.states.MobileState
import co.yap.modules.otp.getOtpMessageFromComposer
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.messages.MessagesRepository
import co.yap.networking.messages.requestdtos.CreateOtpOnboardingRequest
import co.yap.networking.models.RetroApiResponse
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.SingleClickEvent
import co.yap.yapcore.SingleLiveEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.helpers.getCountryCodeForRegion
import co.yap.yapcore.helpers.isValidPhoneNumber
import co.yap.yapcore.leanplum.SignupEvents
import co.yap.yapcore.leanplum.trackEvent
import java.util.*

class MobileViewModel(application: Application) :
    OnboardingChildViewModel<IMobile.State>(application),
    IMobile.ViewModel, IRepositoryHolder<MessagesRepository> {

    override val repository: MessagesRepository = MessagesRepository
    override val state: MobileState = MobileState(application, this)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    override var clickEvent: SingleClickEvent = SingleClickEvent()
    override fun handlePressOnView(id: Int) {
        clickEvent.setValue(id)
    }

    override fun onResume() {
        super.onResume()
        setProgress(20)
    }

    override fun onCreate() {
        super.onCreate()
        trackAdjustPlatformEvent(AdjustEvents.SIGN_UP_START.type)
    }

    override fun getCcp(editText: EditText) {
        editText.requestFocus()
        state.etMobileNumber = editText
        state.etMobileNumber?.requestFocus()
        state.etMobileNumber?.setOnEditorActionListener(onEditorActionListener())
    }

    override fun handlePressOnNext() {
        // Record the updatedDate
        // Send OTP request
        createOtp {}
    }

    override fun onEditorActionListener(): TextView.OnEditorActionListener {
        return TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (state.valid.get()) {
                    handlePressOnNext()
                }
            }
            false
        }
    }

    override fun createOtp(success: (success: Boolean) -> Unit) {
        parentViewModel?.onboardingData?.startTime = Date()
        val mobileNumber: String = state.mobile.trim().replace(" ", "")
        val formattedMobileNumber: String =
            state.countryCode.value?.trim() + " " + state.mobile.trim().replace(
                state.countryCode.value?.trim() ?: "",
                ""
            )
        val countryCode: String = state.countryCode.value?.trim()?.replace("+", "00") ?: ""
        trackEvent(SignupEvents.SIGN_UP_NUMBER.type, countryCode + mobileNumber)
        launch {
            state.loading = true
            when (val response = repository.createOtpOnboarding(
                CreateOtpOnboardingRequest(
                    countryCode,
                    mobileNumber,
                    parentViewModel?.onboardingData?.accountType.toString(),
                    otpMessage = context.getOtpMessageFromComposer(
                        SignupEvents.SIGN_UP_NUMBER.name,
                        "%s1",
                        "%s2",
                        "%s3"
                    )
                )
            )) {
                is RetroApiResponse.Success -> {
                    success.invoke(true)
                    parentViewModel?.onboardingData?.countryCode = countryCode
                    parentViewModel?.onboardingData?.mobileNo = mobileNumber
                    parentViewModel?.onboardingData?.formattedMobileNumber = formattedMobileNumber
                }
                is RetroApiResponse.Error -> {
                    state.error = response.error.message
                    state.mobileError = response.error.message
                    success.invoke(false)
                    trackEvent(SignupEvents.SIGN_UP_NUMBER_ERROR.type, response.error.message)
                }
            }
            state.loading = false
        }
    }

    fun onPhoneNumberTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        state.error = ""
        state.mobileError = ""
        state.valid.set(
            isValidPhoneNumber(
                s.toString(),
                getCountryCodeForRegion(
                    state.countryCode.value.toString().replace("+", "").toInt()
                )
            )
        )
    }


    override val userVerified: MutableLiveData<String> = MutableLiveData()
    override val userVerifier: UserVerifierProvider = UserVerifierProvider()

    override fun verifyUser(countryCode: String, mobileNumber: String) {
        state.loading = true
        LoadConfig(context).initYapRegion(countryCode)
        userVerifier.provideOtpVerifier(countryCode)
            .createOtp(countryCode.replace("+", "00"), mobileNumber) { result ->
                state.loading = false
                if (result.isSuccess && result.getOrNull() == true) {
                    userVerified.value = countryCode
                } else {
                    result.onFailure {
                        state.error = it.message ?: ""
                        state.mobileError = it.message ?: ""
                    }
                }

            }
    }
}