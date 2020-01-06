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

class HouseHoldCreatePassCodeFragment :
    OnboardingChildFragment<IHouseHoldCreatePassCode.ViewModel>(), IHouseHoldCreatePassCode.View {

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_create_passcode
    override fun getBindingVariable() = BR.createPasscodeViewModel

    override val viewModel: IHouseHoldCreatePassCode.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldCreatePassCodeViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer {
            when (it) {
                R.id.btnCreatePasscode -> {
                    findNavController().navigate(R.id.to_emailHouseHoldFragment)
                }
            }
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.clickEvent?.observe(this, Observer {

        })
    }

    override fun onBackPressed(): Boolean = true


}