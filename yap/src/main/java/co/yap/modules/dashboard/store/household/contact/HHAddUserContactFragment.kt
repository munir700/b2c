package co.yap.modules.dashboard.store.household.contact

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserContactBinding
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.translation.Strings
import co.yap.yapcore.helpers.extentions.plus
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHAddUserContactFragment :
    BaseNavViewModelFragmentV2<FragmentHhAddUserContactBinding, IHHAddUserContact.State, HHAddUserContactVM>() {
    override val viewModel: HHAddUserContactVM by viewModels()
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hh_add_user_contact

    override fun getToolBarTitle() =
        getString(Strings.screen_yap_house_hold_user_info_display_text_title)

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                viewModel.verifyMobileNumber {
                    if (it == true) {
                        viewModel.state.request?.value?.countryCode = viewModel.state.countryCode.value
                        viewModel.state.request?.value?.mobileNo = viewModel.state.phone.value?.replace(" ", "") ?: ""
                        navigateForwardWithAnimation(
                            HHAddUserContactFragmentDirections.actionHHAddUserContactFragmentToHouseHoldConfirmPaymentFragment(),
                            arguments?.plus(bundleOf(HouseholdOnboardRequest::class.java.name to viewModel.state.request?.value)),
                            null
                        )
                    }
                }
            }
            R.id.tvBack -> {
                finishActivity()
               // navigateForwardWithAnimation(HHAddUserContactFragmentDirections.actionHHAddUserContactFragmentToSubscriptionSelectionFragment())
            }
        }
    }
}
