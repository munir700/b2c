package co.yap.household.onboarding.passcode

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.databinding.FragmentHhonBoardingPassCodeBinding
import co.yap.modules.webview.WebViewFragment
import co.yap.widgets.NumberKeyboardListener
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_hhon_boarding_pass_code.*

@AndroidEntryPoint
class HHOnBoardingPassCodeFragment :
    BaseNavViewModelFragmentV2<FragmentHhonBoardingPassCodeBinding, IHHOnBoardingPassCode.State, HHOnBoardingPassCodeVM>(),
    NumberKeyboardListener {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hhon_boarding_pass_code
    override fun setHomeAsUpIndicator() = R.drawable.ic_back
    override fun setDisplayHomeAsUpEnabled() = false
    override val viewModel: HHOnBoardingPassCodeVM by viewModels()

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        dialer.setNumberKeyboardListener(this)
        dialer.hideFingerprintView()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnCreatePasscode -> {
                viewModel.createPassCodeRequest {
                    if (it == true) {
                        arguments?.putInt(Constants.INDEX, 80)
                        navigateForwardWithAnimation(
                            HHOnBoardingPassCodeFragmentDirections.toHHOnBoardingEmailFragment(),
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
                    ), showToolBar = false
                )
            }
        }
    }

    override fun onNumberClicked(number: Int, text: String) {
        viewModel.state.passCode.value = dialer.getText()
    }

    override fun onRightButtonClicked() {
        viewModel.state.passCode.value = dialer.getText()
    }
}
