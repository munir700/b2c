package co.yap.household.onboarding.onboarding.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.fragments.OnboardingChildFragment
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldCreatePassCode
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldCreatePassCodeViewModel
import co.yap.widgets.NumberKeyboardListener
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.helpers.Utils
import co.yap.yapcore.helpers.extentions.preventTakeScreenshot
import kotlinx.android.synthetic.main.fragment_house_hold_create_passcode.*

class HouseHoldCreatePassCodeFragment :
    OnboardingChildFragment<IHouseHoldCreatePassCode.ViewModel>(), IHouseHoldCreatePassCode.View,
    NumberKeyboardListener {

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_create_passcode
    override fun getBindingVariable() = BR.createPasscodeViewModel

    override val viewModel: IHouseHoldCreatePassCode.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldCreatePassCodeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preventTakeScreenshot()
        setObservers()
        dialer.setNumberKeyboardListener(this)
        dialer.hideFingerprintView()
    }

    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer {
            when (it) {
                R.id.btnCreatePasscode -> {
                    viewModel.createPassCodeRequest()
                }
                R.id.tvTermsAndConditions -> {
                    Utils.openWebPage(Constants.URL_TERMS_CONDITION, "", activity)
                }
            }
        })
        viewModel.onPasscodeSuccess.observe(this, Observer {
            if (it) findNavController().navigate(R.id.to_emailHouseHoldFragment)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent?.removeObservers(this)
    }

    override fun onBackPressed(): Boolean = false
    override fun onNumberClicked(number: Int, text: String) {
        viewModel.state.passcode = dialer.getText()
        viewModel.state.dialerError = ""
    }

    override fun onLeftButtonClicked() {
    }

    override fun onRightButtonClicked() {
        viewModel.state.dialerError = ""
    }

}