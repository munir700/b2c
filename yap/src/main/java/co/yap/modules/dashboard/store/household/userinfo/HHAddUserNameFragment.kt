package co.yap.modules.dashboard.store.household.userinfo

import android.os.Bundle
import androidx.core.os.bundleOf
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserNameBinding
import co.yap.networking.customers.requestdtos.HouseholdOnboardRequest
import co.yap.translation.Strings
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.enums.AccountType
import co.yap.yapcore.helpers.extentions.plus
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent

class HHAddUserNameFragment :
    BaseNavViewModelFragment<FragmentHhAddUserNameBinding, IHHAddUserName.State, HHAddUserNameVM>() {
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
                                firstName = state.firstName.value,
                                lastName = state.lastName.value,
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
