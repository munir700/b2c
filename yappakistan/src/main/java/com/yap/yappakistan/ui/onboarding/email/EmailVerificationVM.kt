package com.yap.yappakistan.ui.onboarding.email

import android.os.Bundle
import android.util.Patterns
import androidx.annotation.VisibleForTesting
import androidx.core.os.bundleOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yap.core.base.BaseViewModel
import com.yap.core.base.Dispatcher
import com.yap.core.utils.KEY_IS_USER_LOGGED_IN
import com.yap.core.utils.SingleEvent
import com.yap.yappakistan.SessionManager
import com.yap.yappakistan.SharedPreferenceManager
import com.yap.yappakistan.networking.apiclient.base.ApiResponse
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.DemographicDataRequest
import com.yap.yappakistan.networking.microservices.customers.CustomersApi
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.EmailVerificationRequest
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.SaveReferralRequest
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.SignUpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class EmailVerificationVM @ViewModelInject constructor(
    override val viewState: EmailVerificationState,
    private val customersApi: CustomersApi,
    override val sharedPreferenceManager: SharedPreferenceManager,
    override val sessionManager: SessionManager
) : BaseViewModel<IEmailVerification.State>(), IEmailVerification.ViewModel {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _savProfileEvent: MutableLiveData<Boolean> = MutableLiveData()
    override val savProfileEvent: LiveData<Boolean> = _savProfileEvent

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _animationStartEvent: MutableLiveData<Boolean> = MutableLiveData()
    override val animationStartEvent: LiveData<Boolean> = _animationStartEvent

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showErrorPrivate = MutableLiveData<SingleEvent<String?>>()
    override val showError: LiveData<SingleEvent<String?>> get() = showErrorPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val _openSignupSuccess = MutableLiveData<SingleEvent<Bundle>>()
    override val openSignupSuccess: LiveData<SingleEvent<Bundle>> = _openSignupSuccess

    fun onEmailTextChanged(
        s: CharSequence, start: Int, before: Int,
        count: Int
    ) {
        showErrorPrivate.value = SingleEvent(null)
        val emailPattern = Patterns.EMAIL_ADDRESS
        viewState.isValid.value = emailPattern.matcher(s.toString()).matches()
    }

    override fun verifyEmailAndSignup(
        emailVerificationRequest: EmailVerificationRequest,
        signUpRequest: SignUpRequest,
        postDataRequest: DemographicDataRequest
    ) {
        launch {
            showLoading(true)
            emailVerification(emailVerificationRequest) { token ->
                signUpRequest.token = token
                saveProfile(signUpRequest) {
                    sharedPreferenceManager.getReferralInfo()?.let {
                        requestSaveReferral(SaveReferralRequest(it.id, it.date))
                    }
                    postDemographicData(postDataRequest) {
                        getAccountInfo()
                    }
                }
            }
        }
    }

    private fun emailVerification(
        emailVerificationRequest: EmailVerificationRequest,
        success: (token: String) -> Unit
    ) {
        launch {
            val response = customersApi.emailVerification(emailVerificationRequest)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        success.invoke(response.data.data ?: "")
                    }
                    is ApiResponse.Error -> {
                        hideLoading()
                        showErrorMessage(response.error.message)
                        _savProfileEvent.value = false
                    }
                }
            }
        }
    }

    private fun saveProfile(signUpRequest: SignUpRequest, success: () -> Unit) {
        launch {
            val response = customersApi.signUp(signUpRequest)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        saveUserProfileLocally(
                            signUpRequest.passcode,
                            signUpRequest.mobileNo,
                            signUpRequest.countryCode
                        )
                        success.invoke()
                    }
                    is ApiResponse.Error -> {
                        hideLoading()
                        showErrorMessage(response.error.message)
                        _savProfileEvent.value = false
                    }
                }
            }
        }
    }

    private fun postDemographicData(
        demographicDataRequest: DemographicDataRequest,
        success: () -> Unit
    ) {
        launch {
            val response = customersApi.postDemographicData(demographicDataRequest)
            withContext(Dispatchers.Main) {
                when (response) {
                    is ApiResponse.Success -> {
                        success.invoke()
                    }
                    is ApiResponse.Error -> {
                        hideLoading()
                        showErrorMessage(response.error.message)
                        _savProfileEvent.value = false
                    }
                }
            }
        }
    }

    private fun getAccountInfo() {
        launch(Dispatcher.Main) {
            sessionManager.getAccountInfo { errorMessage ->
                if (errorMessage.isNullOrBlank()) {
                    _savProfileEvent.value = true
                    hideLoading()
                } else {
                    hideLoading()
                    showErrorMessage(errorMessage)
                    _savProfileEvent.value = false
                }
            }
        }
    }

    private fun requestSaveReferral(saveReferralRequest: SaveReferralRequest) {
        launch {
            when (val response = customersApi.saveReferralInvitation(saveReferralRequest)) {
                is ApiResponse.Success -> {
                    sharedPreferenceManager.setReferralInfo(null)
                }
                is ApiResponse.Error -> {
                    showAlertMessage(response.error.message, true)
                }
            }
        }
    }

    private fun saveUserProfileLocally(passcode: String?, mobileNo: String?, countryCode: String?) {
        sharedPreferenceManager.save(
            KEY_IS_USER_LOGGED_IN,
            true
        )
        sharedPreferenceManager.savePassCodeWithEncryption(
            passcode ?: ""
        )
        sharedPreferenceManager.saveUserNameWithEncryption(
            mobileNo ?: ""
        )
        sharedPreferenceManager.saveUserDialCodeWithEncryption(
            countryCode?.replace("00", "+") ?: ""
        )
    }

    override fun openSignupSuccessScreen() {
        val bundle =
            if (sessionManager.userAccount.value?.isWaiting == true) bundleOf("IsWaiting" to true) else bundleOf(
                "IsWaiting" to false
            )

        _openSignupSuccess.value = SingleEvent(bundle)
    }

    private fun showErrorMessage(errorMessage: String) {
        showErrorPrivate.value = SingleEvent(errorMessage)
    }
}