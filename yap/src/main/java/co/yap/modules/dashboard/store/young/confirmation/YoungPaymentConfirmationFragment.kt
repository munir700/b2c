package co.yap.modules.dashboard.store.young.confirmation

import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungPaymentConfirmationBinding
import co.yap.modules.kyc.activities.DocumentsDashboardActivity
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.launchActivityForResult
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.MyUserManager

class YoungPaymentConfirmationFragment :
    BaseNavViewModelFragment<FragmentYoungPaymentConfirmationBinding, IYoungPaymentConfirmation.State, YoungPaymentConfirmationVM>() {

    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_payment_confirmation
    override fun getToolBarTitle() =
        getString(Strings.screen_household_payment_confirmation_tool_bar_text)

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGetStarted -> {
                launchActivityForResult<DocumentsDashboardActivity>(
                    init = {
                        putExtra(
                            Constants.name,
                            MyUserManager.user?.currentCustomer?.firstName.toString()
                        )
                        putExtra(Constants.data, false)
                    }, completionHandler = { resultCode, data ->
                        data?.let {
                            val status = it.getStringExtra("status")
                            if (it.getBooleanExtra(Constants.result, false)) {
                                trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EID.type)
                                //  Handler().post { launchAddressSelection(true) }
                                return@let
                            } else if (it.getBooleanExtra(Constants.skipped, false)) {
                                trackEvent(HHUserOnboardingEvents.ONBOARDING_NEW_HH_USER_EID_DECLINED.type)
                                //if (status == KYCAction.ACTION_EID_FAILED.name)
//                                    navigateForward(
//                                        HHOnBoardingCardSelectionFragmentDirections.toHHOnBoardingInvalidEidFragment(),
//                                        arguments
//                                    )
                            }
                        }

                    })
                //navigate(YoungPaymentConfirmationFragmentDirections.actionYoungPaymentConfirmationFragmentToYoungConfirmRelationshipFragment())
            }
        }
    }
}
