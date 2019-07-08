package co.yap.modules.onboarding.viewmodels

import android.app.Application
import co.yap.modules.onboarding.interfaces.IMobile
import co.yap.modules.onboarding.states.MobileState
import co.yap.networking.interfaces.IRepositoryHolder
import co.yap.networking.models.RetroApiResponse
import co.yap.networking.onboarding.ObnoardingRepository
import co.yap.networking.onboarding.requestdtos.CreateOtpRequest
import co.yap.yapcore.SingleLiveEvent

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
        createOtp()
    }

    private fun createOtp() {
        launch {
            when (val response = repository.createOtp(
                CreateOtpRequest(
                    state.countryCode.trim().replace("+", "00"),
                    state.mobile,
                    parentViewModel?.onboardingData?.accountType.toString()
                )
            )) {
                is RetroApiResponse.Success -> {
                    nextButtonPressEvent.postValue(true)
                    parentViewModel!!.onboardingData.countryCode = state.countryCode.trim().replace("+", "00")
                    parentViewModel!!.onboardingData.mobileNo = state.mobile
                }
                is RetroApiResponse.Error -> state.error = response.error.message
            }
        }
    }
}