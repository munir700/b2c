package com.yap.yappakistan.ui.onboarding.email

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.yap.core.base.BaseNavFragment
import com.yap.core.extensions.observe
import com.yap.core.extensions.observeEvent
import com.yap.core.utils.KEY_APP_UUID
import com.yap.core.utils.SingleEvent
import com.yap.core.utils.UtilityFunctions
import com.yap.yappakistan.BR
import com.yap.yappakistan.R
import com.yap.yappakistan.databinding.FragmentEmailVerificationBinding
import com.yap.yappakistan.networking.microservices.authentication.requestdtos.DemographicDataRequest
import com.yap.yappakistan.networking.microservices.customers.requestsdtos.EmailVerificationRequest
import com.yap.yappakistan.ui.onboarding.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmailVerificationFragment :
    BaseNavFragment<FragmentEmailVerificationBinding, IEmailVerification.State, IEmailVerification.ViewModel>(
        R.layout.fragment_email_verification
    ) {
    override fun getBindingVariable(): Int = BR.viewModel

    override val viewModel: IEmailVerification.ViewModel by viewModels<EmailVerificationVM>()
    private val parentViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                verifyEmailAndSignup()
            }
        }
    }

    private fun verifyEmailAndSignup() {
        val emailRequest = EmailVerificationRequest(
            email = viewModel.viewState.email.value ?: "",
            accountType = "B2C_ACCOUNT",
            token = parentViewModel.signupData.token
        )
        val signupRequest = parentViewModel.signupData.apply {
            countryCode = countryCode?.replace("+", "00")
            email = viewModel.viewState.email.value
            whiteListed = false
            accountType = "\"B2C_ACCOUNT\""
        }

        val deviceId: String? = viewModel.sharedPreferenceManager.getValueString(KEY_APP_UUID)
        val demoGraphicsRequest = DemographicDataRequest(
            "SIGNUP",
            Build.VERSION.RELEASE,
            deviceId,
            Build.BRAND,
            if (UtilityFunctions().isEmulator()) "generic" else Build.MODEL,
            "Android"
        )

        viewModel.verifyEmailAndSignup(emailRequest, signupRequest, demoGraphicsRequest)

    }

    private fun openSignupSuccessScreen(errorEvent: SingleEvent<Bundle>) {
        val bundle = errorEvent.getContentIfNotHandled()
        navigate(
            R.id.action_emailVerificationFragment_to_signupCompletedFragment,
            bundle
        )
    }

    private fun showErrorOnUI(errorEvent: SingleEvent<String?>) {
        val errorMessage = errorEvent.getContentIfNotHandled()
        mViewBinding.etEmail.error = errorMessage
        mViewBinding.etEmail.isErrorEnabled = errorMessage != null
    }

    private fun setVerificationLabel(shouldSet: Boolean) {
        if (!shouldSet) return
        viewModel.openSignupSuccessScreen()
    }

    override fun onResume() {
        super.onResume()
        parentViewModel.setProgress(80)
    }

    private fun viewModelObservers() {
        observeEvent(viewModel.showError, ::showErrorOnUI)
        observeEvent(viewModel.openSignupSuccess, ::openSignupSuccessScreen)
        observe(viewModel.savProfileEvent, ::setVerificationLabel)
    }
}