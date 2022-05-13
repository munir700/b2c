package co.yap.modules.dashboard.store.household.subscriptionselection

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentHouseHoldSubscriptionSelctionBinding
import co.yap.modules.onboarding.models.WelcomeContent
import co.yap.modules.webview.WebViewFragment
import co.yap.networking.household.responsedtos.HouseHoldPlan
import co.yap.translation.Strings
import co.yap.translation.Strings.common_button_cancel
import co.yap.translation.Strings.common_button_confirm
import co.yap.translation.Strings.screen_household_set_pin_terms_and_conditions_text
import co.yap.translation.Strings.screen_yap_house_hold_subscription_selection_confirm_message_text
import co.yap.translation.Strings.screen_yap_house_hold_subscription_selection_display_text_months
import co.yap.translation.Strings.screen_yap_house_hold_subscription_selection_display_text_per_month
import co.yap.translation.Strings.screen_yap_house_hold_subscription_selection_display_text_per_year
import co.yap.yapcore.AdjustEvents.Companion.trackAdjustPlatformEvent
import co.yap.yapcore.BaseRVAdapter
import co.yap.yapcore.BaseViewHolder
import co.yap.yapcore.adjust.AdjustEvents
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.interfaces.ManageToolBarListener
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.spannables.kpan.span
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import co.yap.yapcore.leanplum.HHSubscriptionEvents
import co.yap.yapcore.leanplum.HHUserOnboardingEvents
import co.yap.yapcore.leanplum.trackEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_house_hold_subscription_selction.*
import javax.inject.Inject

@AndroidEntryPoint
class SubscriptionSelectionFragment :
    BaseNavViewModelFragmentV2<FragmentHouseHoldSubscriptionSelctionBinding, ISubscriptionSelection.State, SubscriptionSelectionVM>() {
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
                if (!viewModel.state.plansList.value.isNullOrEmpty()) {
                    confirm(
                        message = span {
                            span(
                                getString(
                                    screen_yap_house_hold_subscription_selection_confirm_message_text,
                                    viewModel.state.plansList.value?.get(
                                        viewModel.state.selectedPlanPosition.value ?: 0
                                    )?.type!!,
                                    getString(
                                        screen_yap_house_hold_subscription_selection_display_text_months
                                    )
                                )
                            ) {
                                textColor = requireContext().getColor(R.color.semi_dark)
                            }
                            span(getString(screen_household_set_pin_terms_and_conditions_text)) {
                                onClick = {
                                    startFragment(
                                        fragmentName = WebViewFragment::class.java.name,
                                        bundle = bundleOf(
                                            Constants.PAGE_URL to Constants.URL_TERMS_CONDITION
                                        ),
                                        showToolBar = false
                                    )
                                }
                                textColor = requireContext().getColor(R.color.semi_dark)
                                textDecorationLine = "underline"
                            }
                        },
                        title = if (viewModel.state.selectedPlanPosition.value == 0) "${viewModel.state.monthlyFee.value} ${
                            getString(
                                screen_yap_house_hold_subscription_selection_display_text_per_month
                            )
                        }" else "${viewModel.state.annuallyFee.value} ${
                            getString(
                                screen_yap_house_hold_subscription_selection_display_text_per_year
                            )
                        }",
                        positiveButton = getString(common_button_confirm),
                        negativeButton = getString(common_button_cancel), callback = {
                            trackAdjustPlatformEvent(AdjustEvents.HOUSE_HOLD_MAIN_SUB_PLAN_CONFIRM.type)
                            trackEvent(HHUserOnboardingEvents.ONBOARDING_START_NEW_HH_USER.type)
                            trackEvent(HHSubscriptionEvents.HH_SUB_PLANS_CONFIRM.type)
                            navigateForwardWithAnimation(
                                SubscriptionSelectionFragmentDirections.actionSubscriptionSelectionFragmentToHHAddUserNameFragment(),
                                bundleOf(
                                    HouseHoldPlan::class.java.name to viewModel.state.plansList.value,
                                    Constants.POSITION to viewModel.state.selectedPlanPosition.value
                                ), null
                            )
                        }, negativeCallback = {}
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

    override val viewModel: SubscriptionSelectionVM by viewModels()
}
