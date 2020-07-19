package co.yap.modules.dashboard.store.household.contact

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserContactBinding
import co.yap.modules.dashboard.store.household.userinfo.HHAddUserNameFragmentDirections
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.translation.Strings
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.plus
import kotlinx.android.synthetic.main.fragment_hh_add_user_contact.*

class HHAddUserContactFragment :
    BaseNavViewModelFragment<FragmentHhAddUserContactBinding, IHHAddUserContact.State, HHAddUserContactVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hh_add_user_contact
    override fun getToolBarTitle() =
        getString(Strings.screen_yap_house_hold_user_info_display_text_title)

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        viewModel.clickEvent.observe(this, Observer { onClick(it) })
    }

    private fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                viewModel.verifyMobileNumber {
                    if (it == true) {
                        state.request?.value?.countryCode = state.countryCode.value
                        state.request?.value?.mobileNo = state.phone.value
                        navigateForwardWithAnimation(
                            HHAddUserContactFragmentDirections.actionHHAddUserContactFragmentToHouseHoldConfirmPaymentFragment(),
                            arguments?.plus(bundleOf(HouseholdOnboardRequest::class.java.name to state.request?.value)),null
                        )
                    }
                }

            }
            R.id.tvBack -> {
                navigateForwardWithAnimation(HHAddUserContactFragmentDirections.actionHHAddUserContactFragmentToSubscriptionSelectionFragment())
            }
        }
    }

    override fun onDestroyView() {
        viewModel.clickEvent.removeObservers(this)
        super.onDestroyView()
    }
}
