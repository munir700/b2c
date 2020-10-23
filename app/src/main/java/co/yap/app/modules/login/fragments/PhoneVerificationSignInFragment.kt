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
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.modules.onboarding.enums.AccountType
import co.yap.networking.customers.responsedtos.AccountInfo
import co.yap.yapcore.helpers.SharedPreferenceManager
import co.yap.yapcore.helpers.biometric.BiometricUtil
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.managers.SessionManager

class PhoneVerificationSignInFragment :
    MainChildFragment<IPhoneVerificationSignIn.ViewModel>() {

    override fun getBindingVariable(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.activity_phone_verification

    override val viewModel: IPhoneVerificationSignIn.ViewModel
        get() = ViewModelProviders.of(this).get(PhoneVerificationSignInViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.reverseTimer(10, requireContext())
        viewModel.postDemographicDataResult.observe(this, postDemographicDataObserver)
        viewModel.accountInfo.observe(this, onFetchAccountInfo)

        setUsername()
        setPasscode()
    }

    private val postDemographicDataObserver = Observer<Boolean> {
        viewModel.getAccountInfo()

    }
    private val onFetchAccountInfo = Observer<AccountInfo> {
        it?.run {
            SessionManager.updateCardBalance {  }
            if (accountType == AccountType.B2C_HOUSEHOLD.name) {
                val bundle = Bundle()
                SharedPreferenceManager(requireContext()).setThemeValue(co.yap.yapcore.constants.Constants.THEME_HOUSEHOLD)
                bundle.putBoolean(OnBoardingHouseHoldActivity.EXISTING_USER, false)
                bundle.putParcelable(OnBoardingHouseHoldActivity.USER_INFO, it)
                startActivity(OnBoardingHouseHoldActivity.getIntent(requireContext(), bundle))
                activity?.finish()
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
                            findNavController().navigate(R.id.action_goto_yapDashboardActivity)

                        activity?.finish()
                    } else {
                        val action =
                            PhoneVerificationSignInFragmentDirections.actionPhoneVerificationSignInFragmentToSystemPermissionFragment(
                                Constants.TOUCH_ID_SCREEN_TYPE
                            )
                        findNavController().navigate(action)
                    }

                } else {
                    if (it.otpBlocked == true)
                        startFragment(fragmentName = OtpBlockedInfoFragment::class.java.name)
                    else
                        findNavController().navigate(R.id.action_goto_yapDashboardActivity)

                    activity?.finish()
                }
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