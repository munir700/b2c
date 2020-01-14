package co.yap.household.onboarding.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboarding.fragments.OnboardingChildFragment
import co.yap.household.onboarding.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.household.onboarding.onboarding.viewmodels.HouseHoldNumberRegistrationViewModel
import kotlinx.android.synthetic.main.fragment_house_hold_number_registration.*


class HouseHoldNumberRegistrationFragment :
    OnboardingChildFragment<IHouseHoldNumberRegistration.ViewModel>(),
    IHouseHoldNumberRegistration.View {
    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_number_registration

    override val viewModel: IHouseHoldNumberRegistration.ViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldNumberRegistrationViewModel::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialer.setInPutEditText(etPhoneNumber)
        dialer.hideFingerprintView()


    }

    override fun onResume() {
        super.onResume()
        setObservers()
    }

    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer { it ->
            when (it) {
                R.id.btnConfirm -> {
                    viewModel.state.existingYapUser?.let {
                        if (it) {
//                            viewModel.verifyHouseholdParentMobile()
                            startActivity(
                                Intent(
                                    requireContext(),
                                    HouseHoldCardsSelectionActivity::class.java
                                )
                            )
                        } else {
                            findNavController().navigate(R.id.to_houseHoldCreatePassCodeFragment)
                        }

                    }

                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clickEvent?.removeObservers(this)
    }

    override fun onBackPressed(): Boolean = false
}