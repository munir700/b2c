package co.yap.modules.dashboard.store.young.paymentselection

import androidx.core.os.bundleOf
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungPaymentSelectionBinding
import co.yap.translation.Strings
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment

class YoungPaymentSelectionFragment :
    BaseNavViewModelFragment<FragmentYoungPaymentSelectionBinding, IYoungPaymentSelection.State, YoungPaymentSelectionVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_payment_selection
    override fun getToolBarTitle() =
        getString(Strings.screen_yap_young_payment_selection_display_text_title)

    override fun onClick(id: Int) {
        when (id) {
            R.id.confirmButton -> {
                navigateForwardWithAnimation(
                    YoungPaymentSelectionFragmentDirections.actionYoungPaymentSelectionFragmentToYoungPaymentConfirmationFragment(),
                    bundleOf(
                        Constants.POSITION to state.selectedPlanPosition.value
                    ), null
                )
            }
        }
    }
}
