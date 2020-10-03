package co.yap.modules.dashboard.store.young.paymentselection

import androidx.core.os.bundleOf
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungPaymentSelectionBinding
import co.yap.modules.webview.WebViewFragment
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import co.yap.yapcore.helpers.confirm
import co.yap.yapcore.helpers.extentions.startFragment
import co.yap.yapcore.helpers.spannables.kpan.span

class YoungPaymentSelectionFragment :
    BaseNavViewModelFragment<FragmentYoungPaymentSelectionBinding, IYoungPaymentSelection.State, YoungPaymentSelectionVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_payment_selection
    override fun getToolBarTitle() =
        getString(Strings.screen_yap_young_payment_selection_display_text_title)

    override fun onClick(id: Int) {
        when (id) {
            R.id.confirmButton -> {
                if (!state.plansList.value.isNullOrEmpty()) {
                    confirm(
                        message = span {
                            span(
                                getString(
                                    Strings.screen_yap_house_hold_subscription_selection_confirm_message_text,
                                    state.plansList.value?.get(
                                        state.selectedPlanPosition.value ?: 0
                                    )?.type!!,
                                    getString(Strings.screen_yap_house_hold_subscription_selection_display_text_months)
                                )
                            ) {
                                textColor = requireContext().getColor(R.color.semi_dark)
                            }
                            span(getString(Strings.screen_household_set_pin_terms_and_conditions_text)) {
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
                        title = if (state.selectedPlanPosition.value == 0) "${viewModel.state.monthlyFee.value} ${getString(
                            Strings.screen_yap_house_hold_subscription_selection_display_text_per_month
                        )}" else "${viewModel.state.annuallyFee.value} ${getString(
                            Strings.screen_yap_house_hold_subscription_selection_display_text_per_year
                        )}",
                        positiveButton = getString(Strings.common_button_confirm),
                        negativeButton = getString(Strings.common_button_cancel), callback = {
                            navigateForwardWithAnimation(
                                YoungPaymentSelectionFragmentDirections.actionYoungPaymentSelectionFragmentToYoungPaymentConfirmationFragment(),
                                bundleOf(
                                    Constants.POSITION to state.selectedPlanPosition.value
                                ), null
                            )
                        }, negativeCallback = {}
                    )
                }
            }
        }
    }
}

