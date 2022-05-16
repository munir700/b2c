package co.yap.modules.dashboard.store.household.userinfo

import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserNameBinding
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.translation.Strings
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.extentions.plus
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HHAddUserNameFragment :
    BaseNavViewModelFragmentV2<FragmentHhAddUserNameBinding, IHHAddUserName.State, HHAddUserNameVM>() {
    override val viewModel: HHAddUserNameVM by viewModels()
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hh_add_user_name

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnNext -> {
                trackEvent(HHSubscriptionEvents.HH_PLAN_NAME.type)
                trackAdjustPlatformEvent(AdjustEvents.HOUSE_HOLD_MAIN_PLAN_NAME.type)
                navigateForwardWithAnimation(
                    HHAddUserNameFragmentDirections.actionHHAddUserNameFragmentToHHAddUserContactFragment(),
                    arguments?.plus(
                        bundleOf(
                            HouseholdOnboardRequest::class.java.name to HouseholdOnboardRequest(
                                firstName = viewModel.state.firstName.value,
                                lastName = viewModel.state.lastName.value,
                                accountType = AccountType.B2C_HOUSEHOLD.name
                            )
                        )
                    )
                )
            }
        }
    }

    override fun getToolBarTitle() =
        getString(Strings.screen_yap_house_hold_user_info_display_text_title)
}
