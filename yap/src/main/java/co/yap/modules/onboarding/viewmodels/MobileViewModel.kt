package co.yap.modules.onboarding.viewmodels

import android.app.Application
import android.util.Log
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.states.MobileState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.CreateOtpRequest
import co.yap.yapcore.SingleLiveEvent
import java.util.*

class MobileViewModel(application: Application) : OnboardingChildViewModel<IMobile.State>(application),
    IMobile.ViewModel, IRepositoryHolder<ObnoardingRepository> {
    override val repository: ObnoardingRepository = ObnoardingRepository

    override val state: MobileState = MobileState(application)
    override val nextButtonPressEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    override fun onResume() {
        super.onResume()
        setProgress(20)
    }

    override fun handlePressOnNext() {
        // Record the time
        parentViewModel?.onboardingData?.startTime = Date()

        // Send OTP request
        createOtp()
    }

    private fun createOtp() {

        val mobileNumber: String = state.mobile.trim().replace(state.countryCode.trim(), "")
        val countryCode: String = state.countryCode.trim().replace("+", "00")

        launch {
            state.loading = true
            when (val response = repository.createOtp(
                CreateOtpRequest(
                    countryCode,
                    mobileNumber,
                    parentViewModel?.onboardingData?.accountType.toString()
                )
            )) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.value = true
                    parentViewModel!!.onboardingData.countryCode = countryCode
                    parentViewModel!!.onboardingData.mobileNo = mobileNumber


                }
                is RetroApiResponse.Error -> {
                    state.error = response.error.message
                    state.mobileError = response.error.message
                }
            }
            state.loading = false
        }
    }
}