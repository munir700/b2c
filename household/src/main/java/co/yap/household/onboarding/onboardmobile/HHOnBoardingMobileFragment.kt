package co.yap.household.onboarding.onboardmobile

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonboardingMobileBinding
import co.yap.modules.webview.WebViewFragment
import co.yap.yapcore.constants.Constants

import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hhonboarding_mobile.*


@AndroidEntryPoint
class HHOnBoardingMobileFragment :
    BaseNavViewModelFragmentV2<FragmentHhonboardingMobileBinding, IHHOnBoardingMobile.State, HHOnBoardingMobileVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhonboarding_mobile
    override fun setHomeAsUpIndicator() = R.drawable.ic_back
    override fun setDisplayHomeAsUpEnabled() = false
    override val viewModel: HHOnBoardingMobileVM by viewModels()

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        dialer.showDialerPassCodeView = false
        dialer.setInPutEditText(etPhoneNumber)
        dialer.hideFingerprintView()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnConfirm -> viewModel.verifyHouseholdParentMobile {
                it?.let {
                    if (it.isNotEmpty()) {
                        arguments?.putInt(Constants.INDEX, 50)
                        navigateForwardWithAnimation(
                            HHOnBoardingMobileFragmentDirections.toHHOnBoardingPassCodeFragment(),
                            arguments,
                            null
                        )
                    }
                }
            }
            R.id.tvTermsAndConditions -> {
                startFragment(
                    fragmentName = WebViewFragment::class.java.name, bundle = bundleOf(
                        Constants.PAGE_URL to Constants.URL_TERMS_CONDITION
                    ), showToolBar = true
                )
            }
        }
    }
}
