package co.yap.modules.dashboard.store.household.success

import android.os.Bundle
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHhAddUserSuccessBinding
import co.yap.translation.Strings
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.extentions.share
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.trackEvent
import co.yap.yapcore.managers.SessionManager

class HHAddUserSuccessFragment :
    BaseNavViewModelFragment<FragmentHhAddUserSuccessBinding, IHHAddUserSuccess.State, HHAddUserSuccessVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_hh_add_user_success
    override fun toolBarVisibility() = false
    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnShare -> {
                trackAdjustPlatformEvent(AdjustEvents.HOUSE_HOLD_MAIN_SHARE.type)
                trackEvent(HHSubscriptionEvents.HH_SHARE.type)
                requireContext().share(
                    getString(Strings.screen_yap_house_hold_confirm_payment_share_text).format(
                         state.onBoardRequest?.value?.countryCode.plus(state.onBoardRequest?.value?.mobileNo),
                        state.onBoardRequest?.value?.tempPassCode,
                         Constants.URL_UNIVERSAL_SHARE_PLAY_STORE
                    )
                )
            }
            R.id.btnGoToHouseHold -> {
                finishActivity()
            }

        }
    }
}
