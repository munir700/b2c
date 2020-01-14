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
import co.yap.yapcore.enums.NotificationStatus
import kotlinx.android.synthetic.main.fragment_house_hold_number_registration.*


class HouseHoldNumberRegistrationFragment :
    OnboardingChildFragment<IHouseHoldNumberRegistration.ViewModel>(),
    IHouseHoldNumberRegistration.View {
    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_number_registration

    override val viewModel: HouseHoldNumberRegistrationViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldNumberRegistrationViewModel::class.java)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.parentViewModel?.state?.accountInfo?.run {
            when (NotificationStatus.valueOf(notificationStatuses)) {
                NotificationStatus.PASS_CODE_PENDING -> {
                    findNavController().navigate(R.id.to_houseHoldCreatePassCodeFragment)

                }
                NotificationStatus.PARNET_MOBILE_VERIFICATION_PENDING -> {

                }
                NotificationStatus.EMAIL_PENDING -> {
                    findNavController().navigate(R.id.action_houseHoldNumberRegistrationFragment_to_emailHouseHoldFragment)

                }
                NotificationStatus.ON_BOARDED -> {
                    findNavController().navigate(R.id.action_goto_yapDashboardActivity)
                }
                else -> {

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialer.setInPutEditText(etPhoneNumber)
        dialer.hideFingerprintView()
        viewModel.isParentMobileValid?.observe(this, isParentMobileValid)
    }

    override fun onResume() {
        super.onResume()
        setObservers()
    }

    val isParentMobileValid = Observer<Boolean>
    {
        if (it) {
            findNavController().navigate(R.id.to_houseHoldCreatePassCodeFragment)
        }
    }

    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer { it ->
            when (it) {
                R.id.btnConfirm -> {
                    viewModel.state.existingYapUser?.let {
                        if (it) {
                            startActivity(
                                Intent(
                                    requireContext(),
                                    HouseHoldCardsSelectionActivity::class.java
                                )
                            )
                        } else {
                            viewModel.verifyHouseholdParentMobile()
                            //
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