package co.yap.app.modules.login.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.app.R
import co.yap.app.constants.Constants
import co.yap.app.main.MainChildFragment
import co.yap.app.modules.login.interfaces.IPhoneVerificationSignIn
import co.yap.app.modules.login.viewmodels.PhoneVerificationSignInViewModel
import co.yap.household.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.dashboard.main.activities.YapDashboardActivity
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.enums.YAPThemes
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.extentions.switchTheme
import co.yap.yapcore.helpers.livedata.GetAccountInfoLiveData
import co.yap.yapcore.helpers.livedata.SwitchProfileLiveData
import co.yap.yapcore.managers.MyUserManager

class PhoneVerificationSignInFragment :
    MainChildFragment<IPhoneVerificationSignIn.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_phone_verification

    override val viewModel: PhoneVerificationSignInViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationSignInViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.reverseTimer(10, requireContext())
        viewModel.postDemographicDataResult.observe(this, postDemographicDataObserver)
        setUsername()
        setPasscode()
    }

    private val postDemographicDataObserver = Observer<Boolean> {
        GetAccountInfoLiveData.get().observe(this, onFetchAccountInfo)
    }

    private val onFetchAccountInfo = Observer<AccountInfo?> {
        it?.run {
            if (MyUserManager.shouldGoToHousehold()) {
                MyUserManager.user?.uuid?.let { it1 ->
                    SwitchProfileLiveData.get(it1, this@PhoneVerificationSignInFragment)
                        .observe(this@PhoneVerificationSignInFragment, switchProfileObserver)
                }
            } else {
                if (BiometricUtil.hasBioMetricFeature(requireActivity())
                ) {
                    if (SharedPreferenceManager(requireContext()).getValueBoolien(
                            co.yap.yapcore.constants.Constants.KEY_TOUCH_ID_ENABLED,
                            false
                        )
                    ) {
                        if (it.otpBlocked == true)
                            startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name)
                        else
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

    private val switchProfileObserver = Observer<AccountInfo?> {
        it.run {
            if (MyUserManager.isOnBoarded()) {
                gotoYapDashboard()
            } else {
                context.switchTheme(YAPThemes.HOUSEHOLD())
                launchActivity<OnBoardingHouseHoldActivity>(clearPrevious = true) {
                    putExtra(NAVIGATION_Graph_ID, R.navigation.hh_new_user_onboarding_navigation)
                    putExtra(
                        NAVIGATION_Graph_START_DESTINATION_ID,
                        R.id.HHOnBoardingWelcomeFragment
                    )
                    putExtra(OnBoardingHouseHoldActivity.USER_INFO, MyUserManager.user)
                }
//                launchActivity<OnBoardingHouseHoldActivity>(clearPrevious = true) {
//                    putExtra(OnBoardingHouseHoldActivity.USER_INFO, MyUserManager.user)
//                }
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
                context.switchTheme(YAPThemes.HOUSEHOLD())
                launchActivity<NavHostPresenterActivity>(clearPrevious = true) {
                    putExtra(NAVIGATION_Graph_ID, R.navigation.hh_main_nav_graph)
                    putExtra(NAVIGATION_Graph_START_DESTINATION_ID, R.id.householdDashboardFragment)
                }
                // launchActivity<HouseholdDashboardActivity>(clearPrevious = true)
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