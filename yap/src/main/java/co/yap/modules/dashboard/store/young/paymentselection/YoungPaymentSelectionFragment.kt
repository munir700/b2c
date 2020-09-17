package co.yap.modules.dashboard.store.young.paymentselection

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentYoungPaymentSelectionBinding
import co.yap.translation.Strings
import co.yap.widgets.radiocus.PresetRadioGroup
import co.yap.yapcore.constants.Constants
import co.yap.yapcore.dagger.base.navigation.BaseNavViewModelFragment
import kotlinx.android.synthetic.main.fragment_young_payment_selection.*

class YoungPaymentSelectionFragment :
    BaseNavViewModelFragment<FragmentYoungPaymentSelectionBinding, IYoungPaymentSelection.State, YoungPaymentSelectionVM>() {
    override fun getBindingVariable() = BR.viewModel
    override fun getLayoutId() = R.layout.fragment_young_payment_selection
    override fun getToolBarTitle() =
        getString(Strings.screen_yap_young_payment_selection_display_text_title)

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        selectorGroup?.onCheckedChangeListener = object : PresetRadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(
                radioGroup: View?,
                radioButton: View?,
                isChecked: Boolean,
                checkedId: Int
            ) {
                state.selectedPlanPosition.value = if (checkedId == R.id.monthlyIndicator) 0 else 1
            }
        }
    }

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
