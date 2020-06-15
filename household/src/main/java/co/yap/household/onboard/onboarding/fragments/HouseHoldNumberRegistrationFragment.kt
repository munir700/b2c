package co.yap.household.onboard.onboarding.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import co.yap.household.BR
import co.yap.household.R
import co.yap.household.onboard.cardselection.HouseHoldCardsSelectionActivity
import co.yap.household.onboard.onboarding.existinghousehold.ExistingHouseholdFragment
import co.yap.household.onboard.onboarding.interfaces.IHouseHoldNumberRegistration
import co.yap.household.onboard.onboarding.invalideid.InvalidEIDFragment
import co.yap.household.onboard.onboarding.main.OnBoardingHouseHoldActivity
import co.yap.household.onboard.onboarding.viewmodels.HouseHoldNumberRegistrationViewModel
import co.yap.modules.onboarding.activities.LiteDashboardActivity
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.constants.RequestCodes
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_ID
import co.yap.yapcore.dagger.base.navigation.host.NAVIGATION_Graph_START_DESTINATION_ID
import co.yap.yapcore.dagger.base.navigation.host.NavHostPresenterActivity
import co.yap.yapcore.enums.AccountStatus
import co.yap.yapcore.helpers.extentions.ExtraType
import co.yap.yapcore.helpers.extentions.getValue
import co.yap.yapcore.helpers.extentions.launchActivity
import co.yap.yapcore.helpers.extentions.startFragment
import kotlinx.android.synthetic.main.fragment_house_hold_number_registration.*

class HouseHoldNumberRegistrationFragment :
    OnboardingChildFragment<IHouseHoldNumberRegistration.ViewModel>(),
    IHouseHoldNumberRegistration.View {
    override fun getBindingVariable(): Int = BR.houseHoldViewModel

    override fun getLayoutId(): Int = R.layout.fragment_house_hold_number_registration

    override val viewModel: HouseHoldNumberRegistrationViewModel
        get() = ViewModelProviders.of(this).get(HouseHoldNumberRegistrationViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObservers()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.parentViewModel?.state?.accountInfo?.run {
            if (!notificationStatuses.isNullOrBlank())
                when (AccountStatus.valueOf(notificationStatuses)) {
                    AccountStatus.INVITE_PENDING -> {
                        val bundle = Bundle()
                        bundle.putParcelable(
                            OnBoardingHouseHoldActivity.USER_INFO,
                            viewModel.parentViewModel?.state?.accountInfo
                        )
                        startFragment(ExistingHouseholdFragment::class.java.name, true, bundle)
                    }

                    AccountStatus.PARNET_MOBILE_VERIFICATION_PENDING -> {
                    }
                    AccountStatus.PASS_CODE_PENDING -> {
                        findNavController().navigate(R.id.to_houseHoldCreatePassCodeFragment)
                    }
                    AccountStatus.EMAIL_PENDING -> {
                        findNavController().navigate(R.id.action_houseHoldNumberRegistrationFragment_to_emailHouseHoldFragment)
                    }
                    else -> {
                        launchActivity<NavHostPresenterActivity>(clearPrevious = true) {
                            putExtra(NAVIGATION_Graph_ID, R.navigation.hh_main_nav_graph)
                            putExtra(
                                NAVIGATION_Graph_START_DESTINATION_ID,
                                R.id.householdDashboardFragment
                            )
                        }
//                        launchActivity<HouseholdDashboardActivity>()
//                        activity?.finish()
                    }
                }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCodes.REQUEST_KYC_DOCUMENTS) {
                data?.let {
                    val success =
                        data.getValue(
                            Constants.result,
                            ExtraType.BOOLEAN.name
                        ) as? Boolean
                    val skipped =
                        data.getValue(
                            Constants.skipped,
                            ExtraType.BOOLEAN.name
                        ) as? Boolean

                    success?.let {
                        if (it) {
                            startActivity(
                                HouseHoldCardsSelectionActivity.newIntent(
                                    requireContext(),
                                    false
                                )
                            )
                            activity?.finish()
                        } else {
                            skipped?.let { skip ->
                                if (skip) {
                                    startActivity(
                                        Intent(
                                            requireContext(),
                                            LiteDashboardActivity::class.java
                                        )
                                    )

                                } else {
                                    /* startActivity(
                                         Intent(
                                             requireContext(),
                                             InvalidEIDFragment::class.java
                                         )
                                     )*/

                                    startFragment(InvalidEIDFragment::class.java.name)
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialer.showDialerPassCodeView = false
        dialer.setInPutEditText(etPhoneNumber)
        dialer.hideFingerprintView()
    }

    private val validationResponse = Observer<String>
    {
        if (!it.isNullOrEmpty()) {
            if (it == AccountStatus.ON_BOARDED.toString()) {
                launchActivity<HouseHoldCardsSelectionActivity>()
            } else {
                findNavController().navigate(R.id.to_houseHoldCreatePassCodeFragment)
            }

        } else {
//            findNavController().navigate(R.id.to_houseHoldCreatePassCodeFragment)
            showToast(it)

        }
    }

    override fun setObservers() {
        viewModel.clickEvent?.observe(this, Observer { it ->
            when (it) {
                R.id.btnConfirm -> {
                    viewModel.state.existingYapUser?.let {
                        if (it) {
                            launchActivity<NavHostPresenterActivity>(clearPrevious = false) {
                                putExtra(NAVIGATION_Graph_ID, R.navigation.hh_main_nav_graph)
                                putExtra(
                                    NAVIGATION_Graph_START_DESTINATION_ID,
                                    R.id.householdDashboardFragment
                                )
                            }
//                            launchActivity<HouseholdDashboardActivity>()
                        } else {
                            viewModel.verifyHouseholdParentMobile()
                            //
                        }

                    }

                }
            }
        })
        viewModel.parentMobileValidationResponse?.observe(this, validationResponse)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clickEvent?.removeObservers(this)
        viewModel.parentMobileValidationResponse?.removeObservers(this)
    }

    override fun onBackPressed(): Boolean = false
}