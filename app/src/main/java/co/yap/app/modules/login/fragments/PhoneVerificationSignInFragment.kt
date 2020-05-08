package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.modules.login.interfaces.IPhoneVerificationSignIn
import co.yap.app.modules.login.viewmodels.PhoneVerificationSignInViewModel
import co.yap.household.dashboard.main.HouseholdDashboardActivity
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.modules.onboarding.fragments.OnboardingChildFragment
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.livedata.GetAccountInfoLiveData
import co.yap.yapcore.managers.MyUserManager


class PhoneVerificationSignInFragment :
    OnboardingChildFragment<IPhoneVerificationSignIn.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_phone_verification

    override val viewModel: PhoneVerificationSignInViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationSignInViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.reverseTimer(10, requireContext())
        viewModel.nextButtonPressEvent.observe(this, nextButtonObserver)
        viewModel.verifyOtpResult.observe(this, verifyOtpResultObserver)
        viewModel.postDemographicDataResult.observe(this, postDemographicDataObserver)
//        MyUserManager.onAccountInfoSuccess?.observe(this, onFetchAccountInfo)
        MyUserManager.switchProfile.observe(this, switchProfileObserver)
        setUsername()
        setPasscode()
    }

    override fun onDestroy() {
        viewModel.nextButtonPressEvent.removeObservers(this)
//        MyUserManager.onAccountInfoSuccess?.removeObservers(this)
        super.onDestroy()
    }

    private val nextButtonObserver = Observer<Boolean> {
        viewModel.verifyOtp()
    }

    private val verifyOtpResultObserver = Observer<Boolean> {
        viewModel.postDemographicData()
    }

    private val postDemographicDataObserver = Observer<Boolean> {
        GetAccountInfoLiveData.get().observe(this, onFetchAccountInfo)
    }

    private val onFetchAccountInfo = Observer<AccountInfo?> {
        it?.run {
            if (MyUserManager.shouldGoToHousehold()) {
                MyUserManager.switchProfile()
            } else {
                if (BiometricUtil.isFingerprintSupported
                    && BiometricUtil.isHardwareSupported(requireActivity())
                    && BiometricUtil.isPermissionGranted(requireActivity())
                    && BiometricUtil.isFingerprintAvailable(requireActivity())
                ) {
                    SharedPreferenceManager(requireContext())
                    if (SharedPreferenceManager(requireContext()).getValueBoolien(
                            co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED,
                            false
                        )
                    ) {
                        launchActivity<YapDashboardActivity>(clearPrevious = true)
                    } else {
                        val action =
                            PhoneVerificationSignInFragmentDirections.actionPhoneVerificationSignInFragmentToSystemPermissionFragment(
                                Constants.TOUCH_ID_SCREEN_TYPE
                            )
                        findNavController().navigate(action)
                    }
                } else {
                    if (otpBlocked == true)
                        startFragment(
                            fragmentName = OtpBlockedInfoFragment::class.java.name,
                            clearAllPrevious = true
                        )
                    else
                        launchActivity<YapDashboardActivity>(clearPrevious = true)
                }
            }
        }
    }

    private val switchProfileObserver = Observer<Boolean> {
        if (it) {
            if (MyUserManager.isOnBoarded()) {
                gotoYapDashboard()
            } else {
                launchActivity<OnBoardingHouseHoldActivity>(clearPrevious = true) {
                    putExtra(OnBoardingHouseHoldActivity.USER_INFO, MyUserManager.user)
                }
            }

        }
    }

    private fun gotoYapDashboard() {
        if (BiometricUtil.isFingerprintSupported
            && BiometricUtil.isHardwareSupported(requireActivity())
            && BiometricUtil.isPermissionGranted(requireActivity())
            && BiometricUtil.isFingerprintAvailable(requireActivity())
        ) {
            val action =
                PhoneVerificationSignInFragmentDirections.actionPhoneVerificationSignInFragmentToSystemPermissionFragment(
                    Constants.TOUCH_ID_SCREEN_TYPE
                )
            findNavController().navigate(action)
        } else {
            if (MyUserManager.isExistingUser()) {
                launchActivity<YapDashboardActivity>(clearPrevious = true)
            } else {
                launchActivity<HouseholdDashboardActivity>(clearPrevious = true)
            }
        }
    }

    private fun setUsername() {
        viewModel.state.username =
            arguments?.let { PhoneVerificationSignInFragmentArgs.fromBundle(it).username } as String
    }

    private fun setPasscode() {
        viewModel.state.passcode =
            arguments?.let { PhoneVerificationSignInFragmentArgs.fromBundle(it).passcode } as String
    }


}