package co.yap.modules.dashboard.store.household.subscriptionselection

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHouseHoldSubscriptionSelctionBinding
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.translation.Strings
import co.yap.widgets.radiocus.PresetRadioGroup
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.interfaces.ManageToolBarListener
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import kotlinx.android.synthetic.main.fragment_house_hold_subscription_selction.*
import javax.inject.Inject

class SubscriptionSelectionFragment :
    BaseNavViewModelFragment<FragmentHouseHoldSubscriptionSelctionBinding, ISubscriptionSelection.State, SubscriptionSelectionVM>() {
    @Inject
    lateinit var adapter: Adapter

    @Inject
    lateinit var list: ArrayList<WelcomeContent>
    override fun getBindingVariable(): Int = BR.viewModel
    override fun getLayoutId(): Int = R.layout.fragment_house_hold_subscription_selction
    override fun setHomeAsUpIndicator() = co.yap.yapcore.R.drawable.ic_back_arrow_left
    override fun toolBarVisibility() = true
    override fun getToolBarTitle() =
        getString(Strings.screen_yap_house_hold_user_info_display_text_title)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        if (activity is ManageToolBarListener) {
            (activity as ManageToolBarListener).setupToolbar(activity?.findViewById(R.id.toolBar))
        }
        super.postExecutePendingBindings(savedInstanceState)
        pagerSlider.adapter = adapter
        worm_dots_indicator?.setViewPager2(pagerSlider)
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGetStarted -> {
                if (!state.plansList.value.isNullOrEmpty()) {
                    trackAdjustPlatformEvent(AdjustEvents.HOUSE_HOLD_MAIN_SUB_PLAN_CONFIRM.type)
                    trackEvent(HHUserOnboardingEvents.ONBOARDING_START_NEW_HH_USER.type)
                    trackEvent(HHSubscriptionEvents.HH_SUB_PLANS_CONFIRM.type)
                    navigateForwardWithAnimation(
                        SubscriptionSelectionFragmentDirections.actionSubscriptionSelectionFragmentToHHAddUserNameFragment(),
                        bundleOf(
                            HouseHoldPlan::class.java.name to state.plansList.value,
                            Constants.POSITION to state.selectedPlanPosition.value
                        ), null
                    )
                }
            }
        }
    }

    class Adapter(mValue: ArrayList<WelcomeContent>, navigation: NavController?) :
        BaseRVAdapter<WelcomeContent, SubscriptionSelectionItemVM, BaseViewHolder<WelcomeContent, SubscriptionSelectionItemVM>>(
            mValue,
            navigation
        ) {
        override fun getLayoutId(viewType: Int) = getViewModel(viewType).layoutRes()
        override fun getViewHolder(
            view: View,
            viewModel: SubscriptionSelectionItemVM,
            mDataBinding: ViewDataBinding, viewType: Int
        ) = BaseViewHolder(view, viewModel, mDataBinding)

        override fun getViewModel(viewType: Int) = SubscriptionSelectionItemVM()
        override fun getVariableId() = BR.content
    }
}