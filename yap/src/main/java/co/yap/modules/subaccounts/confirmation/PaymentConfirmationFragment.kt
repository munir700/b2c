package co.yap.modules.subaccounts.confirmation

import android.os.Bundle
import androidx.fragment.app.viewModels
import co.yap.BR
import co.yap.R
import co.yap.databinding.FragmentPaymentConfirmationBinding
import co.yap.translation.Strings
import co.yap.yapcore.hilt.base.navigation.BaseNavViewModelFragmentV2
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentConfirmationFragment :
    BaseNavViewModelFragmentV2<FragmentPaymentConfirmationBinding, IPaymentConfirmation.State, PaymentConfirmationVM>() {
    override fun getBindingVariable() = BR.paymentConfirmationVM
    override fun getLayoutId() = R.layout.fragment_payment_confirmation
    override val viewModel: PaymentConfirmationVM by viewModels()

    override fun getToolBarTitle() =
        getString(Strings.screen_household_payment_confirmation_tool_bar_text)

    override fun setDisplayHomeAsUpEnabled() = false

    override fun postExecutePendingBindings(savedInstanceState: Bundle?) {
        super.postExecutePendingBindings(savedInstanceState)
        setBackButtonDispatcher()
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnGoToDashboard -> navigateForwardWithAnimation(
                PaymentConfirmationFragmentDirections.actionPaymentConfirmationFragmentToHHSalaryProfileFragment(),
                arguments
            )
            R.id.btnSetUpNow -> navigateForwardWithAnimation(
                PaymentConfirmationFragmentDirections.actionPaymentConfirmationFragmentToRecurringPaymentFragment(),
                arguments
            )
            R.id.includeImageView -> navigateForwardWithAnimation(
                PaymentConfirmationFragmentDirections.actionPaymentConfirmationFragmentToHHProfileFragment(),
                arguments
            )
        }
    }
}
